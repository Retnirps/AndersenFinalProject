package com.majestadev.rickandmortyguide.main.view.di.episodes.details

import androidx.lifecycle.ViewModel
import com.majestadev.rickandmortyguide.main.di.ViewModelKey
import com.majestadev.rickandmortyguide.main.view.viewmodel.details.EpisodeDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface EpisodeDetailsFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(EpisodeDetailsViewModel::class)
    fun bindEpisodeDetailsViewModel(episodeDetailsViewModel: EpisodeDetailsViewModel): ViewModel
}