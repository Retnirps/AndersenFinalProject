package com.majestadev.rickandmortyguide.main.fragment.characters

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.majestadev.domain.dto.CharacterFilter
import com.majestadev.rickandmortyguide.R
import com.majestadev.rickandmortyguide.databinding.FragmentCharactersBinding
import com.majestadev.rickandmortyguide.main.di.Injector
import com.majestadev.rickandmortyguide.main.fragment.main.MainFragmentDirections
import com.majestadev.rickandmortyguide.main.util.RecyclerViewLoadStateAdapter
import com.majestadev.rickandmortyguide.main.view.viewmodel.CharactersViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersFragment : Fragment(), SearchView.OnQueryTextListener {
    private var binding: FragmentCharactersBinding? = null
    private var adapter: CharactersRecyclerViewAdapter? = null
    private var searchJob: Job? = null
    private var filterFlag: Boolean = false
    private var characterFilter: CharacterFilter? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<CharactersViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Injector.plusCharactersFragmentComponent()?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharactersBinding.inflate(layoutInflater)
        val view = binding?.root

        setHasOptionsMenu(true)

        adapter = CharactersRecyclerViewAdapter { characterValue ->
            val action =
                MainFragmentDirections.actionMainFragmentToCharacterDetailsFragment(characterValue.id)
            findNavController().navigate(action)
            setMenuVisibility(false)
        }
        binding?.charactersRecyclerView?.adapter = adapter?.withLoadStateFooter(
            RecyclerViewLoadStateAdapter()
        )

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL)
        binding?.charactersRecyclerView?.layoutManager = staggeredGridLayoutManager

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("filter_character")
            ?.observe(
                viewLifecycleOwner
            ) { json ->
                characterFilter =
                    Gson().fromJson(json, object : TypeToken<CharacterFilter>() {}.type)
                characterFilter?.let { startFilterJob(it) }
            }

        binding?.swipeRefreshLayout?.setOnRefreshListener {
            if (characterFilter != null) {
                characterFilter?.let { startFilterJob(it) }
            } else {
                startSearchJob()
            }

            adapter?.refresh()
            binding?.swipeRefreshLayout?.isRefreshing = false
        }

        startSearchJob()
        setUpAdapter()

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.filter_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.filter_menu) {
            filterFlag = if (filterFlag) {
                startSearchJob()
                false
            } else {
                findNavController().navigate(R.id.action_mainFragment_to_filterCharactersFragment)
                setMenuVisibility(false)
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startSearchJob() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.getCharacters().observe(viewLifecycleOwner, {
                adapter?.submitData(lifecycle, it)
                binding?.charactersRecyclerView?.layoutAnimation
            })
        }
    }

    private fun startFilterJob(filter: CharacterFilter) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.getFilteredCharacters(filter).observe(viewLifecycleOwner, {
                adapter?.submitData(lifecycle, it)
            })
        }
    }

    private fun setUpAdapter() {
        adapter?.addLoadStateListener { loadState ->

            if (loadState.refresh is LoadState.Loading) {
                binding?.nothingLabelTextView?.visibility = View.GONE
                binding?.charactersRecyclerView?.visibility = View.GONE
                binding?.progressIndicator?.isVisible = true
            } else {
                binding?.progressIndicator?.isVisible = false
                binding?.charactersRecyclerView?.visibility = View.VISIBLE

                if (adapter?.itemCount == 0) {
                    binding?.nothingLabelTextView?.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            characterFilter =
                CharacterFilter(
                    "%$query%",
                    null,
                    null,
                    null,
                    null
                )
            characterFilter?.let { startFilterJob(it) }
        }

        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            characterFilter =
                CharacterFilter(
                    "%$query%",
                    null,
                    null,
                    null,
                    null
                )
            characterFilter?.let { startFilterJob(it) }
        }

        return true
    }
}