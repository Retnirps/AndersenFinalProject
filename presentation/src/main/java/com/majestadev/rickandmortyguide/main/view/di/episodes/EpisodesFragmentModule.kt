package com.majestadev.rickandmortyguide.main.view.di.episodes

import androidx.lifecycle.ViewModel
import com.majestadev.rickandmortyguide.main.di.ViewModelKey
import com.majestadev.rickandmortyguide.main.view.viewmodel.EpisodesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface EpisodesFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(EpisodesViewModel::class)
    fun bindEpisodesViewModel(episodesViewModel: EpisodesViewModel): ViewModel
}