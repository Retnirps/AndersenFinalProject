package com.majestadev.rickandmortyguide.main.fragment.locations

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
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
import com.majestadev.domain.dto.LocationFilter
import com.majestadev.rickandmortyguide.R
import com.majestadev.rickandmortyguide.databinding.FragmentLocationsBinding
import com.majestadev.rickandmortyguide.main.di.Injector
import com.majestadev.rickandmortyguide.main.fragment.main.MainFragmentDirections
import com.majestadev.rickandmortyguide.main.util.RecyclerViewLoadStateAdapter
import com.majestadev.rickandmortyguide.main.view.viewmodel.LocationsViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationsFragment : Fragment(), SearchView.OnQueryTextListener {
    private var binding: FragmentLocationsBinding? = null
    private var adapter: LocationsRecyclerViewAdapter? = null
    private var searchJob: Job? = null
    private var filterFlag: Boolean = false
    private var locationFilter: LocationFilter? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<LocationsViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Injector.plusLocationsFragmentComponent()?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocationsBinding.inflate(layoutInflater)
        val view = binding?.root

        setHasOptionsMenu(true)

        adapter = LocationsRecyclerViewAdapter { locationDetails ->
            val action =
                MainFragmentDirections.actionMainFragmentToLocationDetailsFragment(locationDetails.id)
            findNavController().navigate(action)
            setMenuVisibility(false)
        }
        binding?.locationsRecyclerView?.adapter = adapter?.withLoadStateFooter(
            RecyclerViewLoadStateAdapter()
        )

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL)
        binding?.locationsRecyclerView?.layoutManager = staggeredGridLayoutManager

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("filter_location")
            ?.observe(
                viewLifecycleOwner
            ) { json ->
                locationFilter =
                    Gson().fromJson(json, object : TypeToken<LocationFilter>() {}.type)
                locationFilter?.let { startFilterJob(it) }
            }

        binding?.swipeRefreshLayout?.setOnRefreshListener {
            if (locationFilter != null) {
                locationFilter?.let { startFilterJob(it) }
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.filter_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)

        val filterItem = menu.findItem(R.id.action_filter)
        if (filterFlag && locationFilter != null) {
            filterItem.setIcon(R.drawable.ic_filter_active)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_filter) {
            filterFlag = if (filterFlag) {
                startSearchJob()
                locationFilter = null
                item.setIcon(R.drawable.ic_filter)
                false
            } else {
                findNavController().navigate(R.id.action_mainFragment_to_filterLocationsFragment)
                setMenuVisibility(false)
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startSearchJob() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.getLocations().observe(viewLifecycleOwner, {
                adapter?.submitData(lifecycle, it)
            })
        }
    }

    private fun startFilterJob(filter: LocationFilter) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.getFilteredLocations(filter).observe(viewLifecycleOwner, {
                adapter?.submitData(lifecycle, it)
            })
        }
    }

    private fun setUpAdapter() {
        adapter?.addLoadStateListener { loadState ->

            if (loadState.refresh is LoadState.Loading) {
                binding?.nothingLabelTextView?.visibility = View.GONE
                binding?.locationsRecyclerView?.visibility = View.GONE
                binding?.progressIndicator?.isVisible = true
            } else {
                binding?.progressIndicator?.isVisible = false
                binding?.locationsRecyclerView?.visibility = View.VISIBLE

                if (adapter?.itemCount == 0) {
                    binding?.nothingLabelTextView?.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            locationFilter = LocationFilter("%$query%", null, null)
            locationFilter?.let { startFilterJob(it) }
        }

        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            locationFilter = LocationFilter("%$query%", null, null)
            locationFilter?.let { startFilterJob(it) }
        }

        return true
    }
}