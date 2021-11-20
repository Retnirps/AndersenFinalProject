package com.majestadev.rickandmortyguide.main.fragment.episodes

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
import com.majestadev.domain.dto.EpisodeFilter
import com.majestadev.rickandmortyguide.R
import com.majestadev.rickandmortyguide.databinding.FragmentEpisodesBinding
import com.majestadev.rickandmortyguide.main.di.Injector
import com.majestadev.rickandmortyguide.main.fragment.main.MainFragmentDirections
import com.majestadev.rickandmortyguide.main.util.RecyclerViewLoadStateAdapter
import com.majestadev.rickandmortyguide.main.view.viewmodel.EpisodesViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodesFragment : Fragment(), SearchView.OnQueryTextListener {
    private var binding: FragmentEpisodesBinding? = null
    private var adapter: EpisodesRecyclerViewAdapter? = null
    private var searchJob: Job? = null
    private var filterFlag: Boolean = false
    private var episodeFilter: EpisodeFilter? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<EpisodesViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Injector.plusEpisodesFragmentComponent()?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEpisodesBinding.inflate(layoutInflater)
        val view = binding?.root

        setHasOptionsMenu(true)

        adapter = EpisodesRecyclerViewAdapter { episodeDetails ->
            val action =
                MainFragmentDirections.actionMainFragmentToEpisodeDetailsFragment(episodeDetails.id)
            findNavController().navigate(action)
            setMenuVisibility(false)
        }
        binding?.episodesRecyclerView?.adapter = adapter?.withLoadStateFooter(
            RecyclerViewLoadStateAdapter()
        )

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL)
        binding?.episodesRecyclerView?.layoutManager = staggeredGridLayoutManager

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("filter_episode")
            ?.observe(
                viewLifecycleOwner
            ) { json ->
                episodeFilter =
                    Gson().fromJson(json, object : TypeToken<EpisodeFilter>() {}.type)
                episodeFilter?.let { startFilterJob(it) }
            }

        binding?.swipeRefreshLayout?.setOnRefreshListener {
            if (episodeFilter != null) {
                episodeFilter?.let { startFilterJob(it) }
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

        val filterItem = menu.findItem(R.id.action_filter)
        if (filterFlag && episodeFilter != null) {
            filterItem.setIcon(R.drawable.ic_filter_active)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_filter) {
            filterFlag = if (filterFlag) {
                startSearchJob()
                episodeFilter = null
                item.setIcon(R.drawable.ic_filter)
                false
            } else {
                findNavController().navigate(R.id.action_mainFragment_to_filterEpisodesFragment)
                setMenuVisibility(false)
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startSearchJob() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.getEpisodes().observe(viewLifecycleOwner, {
                adapter?.submitData(lifecycle, it)
            })
        }
    }

    private fun startFilterJob(filter: EpisodeFilter) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.getFilteredEpisodes(filter).observe(viewLifecycleOwner, {
                adapter?.submitData(lifecycle, it)
            })
        }
    }

    private fun setUpAdapter() {
        adapter?.addLoadStateListener { loadState ->

            if (loadState.refresh is LoadState.Loading) {
                binding?.nothingLabelTextView?.visibility = View.GONE
                binding?.episodesRecyclerView?.visibility = View.GONE
                binding?.progressIndicator?.isVisible = true
            } else {
                binding?.progressIndicator?.isVisible = false
                binding?.episodesRecyclerView?.visibility = View.VISIBLE

                if (adapter?.itemCount == 0) {
                    binding?.nothingLabelTextView?.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            episodeFilter = EpisodeFilter("%$query%", null)
            episodeFilter?.let { startFilterJob(it) }
        }

        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            episodeFilter = EpisodeFilter("%$query%", null)
            episodeFilter?.let { startFilterJob(it) }
        }

        return true
    }
}