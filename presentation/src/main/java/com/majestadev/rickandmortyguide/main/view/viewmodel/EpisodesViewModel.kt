package com.majestadev.rickandmortyguide.main.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.majestadev.domain.dto.EpisodeFilter
import com.majestadev.domain.interactors.episodes.EpisodesInteractor
import com.majestadev.rickandmortyguide.main.di.Injector
import javax.inject.Inject

class EpisodesViewModel @Inject constructor(
    val episodesInteractor: EpisodesInteractor
) : ViewModel() {

    fun getEpisodes() = episodesInteractor.getEpisodes().cachedIn(viewModelScope)

    fun getFilteredEpisodes(episodeFilter: EpisodeFilter) =
        episodesInteractor.getFilteredEpisodes(episodeFilter).cachedIn(viewModelScope)

    override fun onCleared() {
        super.onCleared()
        Injector.clearEpisodesFragmentComponent()
    }
}