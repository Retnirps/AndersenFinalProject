package com.majestadev.rickandmortyguide.main.fragment.episodes.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.majestadev.domain.dto.EpisodeFilter
import com.majestadev.rickandmortyguide.databinding.FragmentFilterEpisodesBinding

class FilterEpisodesFragment : Fragment() {
    private var binding: FragmentFilterEpisodesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterEpisodesBinding.inflate(layoutInflater)
        val view = binding?.root

        binding?.cancelButton?.setOnClickListener {
            findNavController().popBackStack()
        }

        binding?.setFiltersButton?.setOnClickListener {
            val episodeCode = binding?.episodeCodeEditText?.text.toString().let {
                if (it.isBlank()) null else it
            }

            val episodeFilter = EpisodeFilter(null, episodeCode)
            val json = Gson().toJson(episodeFilter)

            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                "filter_episode",
                json
            )
            findNavController().popBackStack()
        }

        return view
    }
}