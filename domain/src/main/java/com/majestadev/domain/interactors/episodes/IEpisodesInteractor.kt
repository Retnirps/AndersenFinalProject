package com.majestadev.domain.interactors.episodes

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.majestadev.domain.dto.EpisodeFilter
import com.majestadev.domain.entity.network.episodes.EpisodeDetails

interface IEpisodesInteractor {
    fun getEpisodes(): LiveData<PagingData<EpisodeDetails>>
    fun getFilteredEpisodes(episodeFilter: EpisodeFilter): LiveData<PagingData<EpisodeDetails>>
    suspend fun getMultipleEpisodes(characterId: Int, episodesIds: String): List<EpisodeDetails>
    suspend fun getEpisodeById(id: Int): EpisodeDetails?
}