package com.majestadev.data.network

import com.majestadev.domain.dto.CharacterFilter
import com.majestadev.domain.dto.EpisodeFilter
import com.majestadev.domain.dto.LocationFilter

class Network(private val rickAndMortyApi: RickAndMortyApi) {

    suspend fun getCharacters(page: Int) =
        rickAndMortyApi.getCharacters(
            null,
            null,
            null,
            null,
            null,
            page
        )

    suspend fun getFilteredCharacters(
        characterFilter: CharacterFilter,
        page: Int
    ) = rickAndMortyApi
        .getCharacters(
            characterFilter.name,
            characterFilter.status,
            characterFilter.species,
            characterFilter.type,
            characterFilter.gender,
            page
        )

    suspend fun getCharacterById(id: Int) = rickAndMortyApi.getCharacterById(id)

    suspend fun getMultipleCharacters(charactersIds: String) =
        rickAndMortyApi.getMultipleCharacters(charactersIds)

    suspend fun getEpisodes(page: Int) =
        rickAndMortyApi.getEpisodes(
            null,
            null,
            page
        )

    suspend fun getFilteredEpisodes(
        episodeFilter: EpisodeFilter,
        page: Int
    ) = rickAndMortyApi
        .getEpisodes(
            episodeFilter.name,
            episodeFilter.episode,
            page
        )

    suspend fun getEpisodeById(id: Int) = rickAndMortyApi.getEpisodeById(id)

    suspend fun getMultipleEpisodes(episodesIds: String) =
        rickAndMortyApi.getMultipleEpisodes(episodesIds)

    suspend fun getLocations(page: Int) =
        rickAndMortyApi.getLocations(
            null,
            null,
            null,
            page
        )

    suspend fun getFilteredLocations(
        locationFilter: LocationFilter,
        page: Int
    ) = rickAndMortyApi
        .getLocations(
            locationFilter.name,
            locationFilter.type,
            locationFilter.dimension,
            page
        )

    suspend fun getLocationById(id: Int) = rickAndMortyApi.getLocationById(id)
}