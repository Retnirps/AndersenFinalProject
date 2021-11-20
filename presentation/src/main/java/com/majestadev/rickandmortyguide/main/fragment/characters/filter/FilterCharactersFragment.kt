package com.majestadev.rickandmortyguide.main.fragment.characters.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.majestadev.domain.dto.CharacterFilter
import com.majestadev.rickandmortyguide.R
import com.majestadev.rickandmortyguide.databinding.FragmentFilterCharactersBinding

class FilterCharactersFragment : Fragment() {
    private var binding: FragmentFilterCharactersBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterCharactersBinding.inflate(layoutInflater)
        val view = binding?.root

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.status_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding?.statusSpinner?.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.gender_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding?.genderSpinner?.adapter = adapter
        }

        binding?.cancelButton?.setOnClickListener {
            findNavController().popBackStack()
        }

        binding?.setFiltersButton?.setOnClickListener {
            val status = binding?.statusSpinner?.selectedItem.toString().let {
                if (it.isBlank()) null else it
            }
            val species = binding?.speciesEditText?.text.toString().let {
                if (it.isBlank()) null else it
            }
            val type = binding?.typeEditText?.text.toString().let {
                if (it.isBlank()) null else it
            }
            val gender = binding?.genderSpinner?.selectedItem.toString().let {
                if (it.isBlank()) null else it
            }

            val characterFilter = CharacterFilter(null, status, species, type, gender)
            val json = Gson().toJson(characterFilter)

            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                "filter_character",
                json
            )
            findNavController().popBackStack()
        }

        return view
    }
}