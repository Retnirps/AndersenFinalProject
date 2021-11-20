package com.majestadev.rickandmortyguide.main.view.di.episodes.details

import com.majestadev.rickandmortyguide.main.di.FragmentScope
import com.majestadev.rickandmortyguide.main.fragment.episodes.details.EpisodeDetailsFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [EpisodeDetailsFragmentModule::class])
interface EpisodeDetailsFragmentComponent {
    fun inject(episodeDetailsFragment: EpisodeDetailsFragment)
}