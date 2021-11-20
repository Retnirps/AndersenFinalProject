package com.majestadev.domain.interactors.episodes

import com.majestadev.domain.dto.EpisodeFilter
import com.majestadev.domain.repository.IRickAndMortyRepository
import javax.inject.Inject

class EpisodesInteractor @Inject constructor(
    private val repository: IRickAndMortyRepository
) : IEpisodesInteractor {

    override fun getEpisodes() = repository.getEpisodes()

    override fun getFilteredEpisodes(episodeFilter: EpisodeFilter) =
        repository.getFilteredEpisodes(episodeFilter)

    override suspend fun getMultipleEpisodes(characterId: Int, episodesIds: String) =
        repository.getMultipleEpisodes(characterId, episodesIds)

    override suspend fun getEpisodeById(id: Int) = repository.getEpisodeById(id)
}