package com.majestadev.rickandmortyguide.main.fragment.locations.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.majestadev.rickandmortyguide.R
import com.majestadev.rickandmortyguide.databinding.FragmentLocationDetailsBinding
import com.majestadev.rickandmortyguide.main.di.Injector
import com.majestadev.rickandmortyguide.main.view.viewmodel.details.LocationDetailsViewModel
import javax.inject.Inject

class LocationDetailsFragment : Fragment() {
    private var binding: FragmentLocationDetailsBinding? = null
    private val args by navArgs<LocationDetailsFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<LocationDetailsViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Injector.plusLocationDetailsFragmentComponent()?.inject(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocationDetailsBinding.inflate(layoutInflater)
        val view = binding?.root

        val adapter = LocationDetailsRecyclerViewAdapter { characterDetails ->
            val action = LocationDetailsFragmentDirections
                .actionLocationDetailsFragmentToCharacterDetailsFragment(characterDetails.id)
            findNavController().navigate(action)
        }
        binding?.locationResidentsRecyclerView?.adapter = adapter

        viewModel.locationDetailsLiveData.observe(viewLifecycleOwner, { locationDetails ->
            binding?.locationNameTextView?.text = "Name: ${locationDetails.name}"
            binding?.typeTextView?.text = "Type: ${locationDetails.type}"
            binding?.dimensionTextView?.text = "Dimension: ${locationDetails.dimension}"
        })

        viewModel.residentsLiveData.observe(viewLifecycleOwner, { residents ->
            adapter.setData(residents)

            binding?.residentsLabelTextView?.text =
                resources.getString(R.string.residents) + " (${residents.size})"
        })

        viewModel.getLocationById(args.locationId)

        return view
    }
}