package com.majestadev.rickandmortyguide.main.fragment.episodes.details

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
import com.majestadev.rickandmortyguide.databinding.FragmentEpisodeDetailsBinding
import com.majestadev.rickandmortyguide.main.di.Injector
import com.majestadev.rickandmortyguide.main.view.viewmodel.details.EpisodeDetailsViewModel
import javax.inject.Inject

class EpisodeDetailsFragment : Fragment() {
    private var binding: FragmentEpisodeDetailsBinding? = null
    private val args by navArgs<EpisodeDetailsFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<EpisodeDetailsViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Injector.plusEpisodeDetailsFragmentComponent()?.inject(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEpisodeDetailsBinding.inflate(layoutInflater)
        val view = binding?.root

        val adapter = EpisodeDetailsRecyclerViewAdapter { characterDetails ->
            val action =
                EpisodeDetailsFragmentDirections.actionEpisodeDetailsFragmentToCharacterDetailsFragment(
                    characterDetails.id
                )
            findNavController().navigate(action)
        }
        binding?.episodeCharactersRecyclerView?.adapter = adapter

        viewModel.episodeDetailsLiveData.observe(viewLifecycleOwner, { episodeDetails ->
            binding?.nameTextView?.text = "Name: ${episodeDetails.name}"
            binding?.airDateTextView?.text = "Air Date: ${episodeDetails.airDate}"
            binding?.episodeNumberTextView?.text = "Episode: ${episodeDetails.episode}"
        })

        viewModel.charactersLiveData.observe(viewLifecycleOwner, { characters ->
            adapter.setData(characters)

            binding?.charactersLabelTextView?.text =
                resources.getString(R.string.characters) + " (${characters.size})"
        })

        viewModel.getEpisodeById(args.episodeId)

        return view
    }
}