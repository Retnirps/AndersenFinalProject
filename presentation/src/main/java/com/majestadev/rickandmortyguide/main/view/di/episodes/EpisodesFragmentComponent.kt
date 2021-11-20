package com.majestadev.rickandmortyguide.main.view.di.episodes

import com.majestadev.rickandmortyguide.main.di.FragmentScope
import com.majestadev.rickandmortyguide.main.fragment.episodes.EpisodesFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [EpisodesFragmentModule::class])
interface EpisodesFragmentComponent {
    fun inject(episodesFragment: EpisodesFragment)
}