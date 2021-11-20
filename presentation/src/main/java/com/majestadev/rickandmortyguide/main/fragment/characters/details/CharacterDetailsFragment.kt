package com.majestadev.rickandmortyguide.main.fragment.characters.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.majestadev.domain.util.RegexUtil
import com.majestadev.rickandmortyguide.R
import com.majestadev.rickandmortyguide.databinding.FragmentCharacterDetailsBinding
import com.majestadev.rickandmortyguide.main.di.Injector
import com.majestadev.rickandmortyguide.main.view.viewmodel.details.CharacterDetailsViewModel
import javax.inject.Inject

class CharacterDetailsFragment : Fragment() {
    private var binding: FragmentCharacterDetailsBinding? = null
    private val args by navArgs<CharacterDetailsFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<CharacterDetailsViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Injector.plusCharacterDetailsFragmentComponent()?.inject(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharacterDetailsBinding.inflate(layoutInflater)
        val view = binding?.root

        binding?.collapsingToolbarLayout?.setExpandedTitleColor(
            ContextCompat.getColor(requireContext(), R.color.expanded_toolbar_text_color)
        )

        binding?.collapsingToolbarLayout?.setCollapsedTitleTextColor(
            ContextCompat.getColor(requireContext(), R.color.collapsed_toolbar_text_color)
        )

        val adapter = CharacterDetailsRecyclerViewAdapter { episodeDetails ->
            val action =
                CharacterDetailsFragmentDirections.actionCharacterDetailsFragmentToEpisodeDetailsFragment(
                    episodeDetails.id
                )
            findNavController().navigate(action)
        }
        binding?.characterEpisodesRecyclerView?.adapter = adapter

        viewModel.characterDetailsLiveData.observe(viewLifecycleOwner, { characterDetails ->
            binding?.collapsingToolbarLayout?.title = characterDetails.name
            binding?.statusTextView?.text = "Status: ${characterDetails.status}"
            binding?.speciesTextView?.text = "Species: ${characterDetails.species}"
            binding?.genderTextView?.text = "Gender: ${characterDetails.gender}"
            binding?.originCardView?.setOnClickListener {
                if (characterDetails.origin.name == "unknown") {
                    Toast.makeText(requireContext(), "Unknown place", Toast.LENGTH_SHORT).show()
                } else {
                    val action = CharacterDetailsFragmentDirections
                        .actionCharacterDetailsFragmentToLocationDetailsFragment(
                            RegexUtil.extractIdFromUrl(characterDetails.origin.url)
                        )
                    findNavController().navigate(action)
                }
            }
            binding?.originNameTextView?.text = "Origin: ${characterDetails.origin.name}"
            binding?.locationCardView?.setOnClickListener {
                if (characterDetails.location.name == "unknown") {
                    Toast.makeText(requireContext(), "Unknown place", Toast.LENGTH_SHORT).show()
                } else {
                    val action = CharacterDetailsFragmentDirections
                        .actionCharacterDetailsFragmentToLocationDetailsFragment(
                            RegexUtil.extractIdFromUrl(characterDetails.location.url)
                        )
                    findNavController().navigate(action)
                }
            }
            binding?.locationNameTextView?.text = "Location: ${characterDetails.location.name}"
            binding?.characterImageView?.let {
                Glide
                    .with(requireContext())
                    .load(characterDetails.image)
                    .placeholder(
                        ContextCompat.getDrawable(requireContext(), R.drawable.transparent)
                    )
                    .into(it)
            }
        })

        viewModel.episodesLiveData.observe(viewLifecycleOwner, { episodes ->
            adapter.setData(episodes)

            binding?.episodesLabelTextView?.text =
                resources.getString(R.string.episodes) + " (${episodes.size})"
        })

        viewModel.getCharacterById(args.characterId)

        return view
    }
}