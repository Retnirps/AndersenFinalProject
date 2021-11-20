package com.majestadev.rickandmortyguide.main.fragment.locations.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.majestadev.domain.dto.LocationFilter
import com.majestadev.rickandmortyguide.databinding.FragmentFilterLocationsBinding

class FilterLocationsFragment : Fragment() {
    private var binding: FragmentFilterLocationsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterLocationsBinding.inflate(layoutInflater)
        val view = binding?.root

        binding?.cancelButton?.setOnClickListener {
            findNavController().popBackStack()
        }

        binding?.setFiltersButton?.setOnClickListener {
            val type = binding?.typeEditText?.text.toString().let {
                if (it.isBlank()) null else it
            }
            val dimension = binding?.dimensionEditText?.text.toString().let {
                if (it.isBlank()) null else it
            }

            val episodeFilter = LocationFilter(null, type, dimension)
            val json = Gson().toJson(episodeFilter)

            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                "filter_location",
                json
            )
            findNavController().popBackStack()
        }

        return view
    }
}