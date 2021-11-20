package com.majestadev.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.majestadev.domain.dto.CharacterFilter
import com.majestadev.domain.dto.EpisodeFilter
import com.majestadev.domain.dto.LocationFilter
import com.majestadev.domain.entity.network.characters.CharacterDetails
import com.majestadev.domain.entity.network.episodes.EpisodeDetails
import com.majestadev.domain.entity.network.locations.LocationDetails

interface IRickAndMortyRepository {

    fun getCharacters(): LiveData<PagingData<CharacterDetails>>

    suspend fun getMultipleCharactersInEpisode(
        episodeId: Int,
        charactersIds: String
    ): List<CharacterDetails>

    fun getFilteredCharacters(
        characterFilter: CharacterFilter
    ): LiveData<PagingData<CharacterDetails>>

    suspend fun getCharacterById(id: Int): CharacterDetails?

    fun getEpisodes(): LiveData<PagingData<EpisodeDetails>>

    suspend fun getMultipleEpisodes(characterId: Int, episodesIds: String): List<EpisodeDetails>

    suspend fun getEpisodeById(id: Int): EpisodeDetails?

    fun getFilteredEpisodes(
        episodeFilter: EpisodeFilter
    ): LiveData<PagingData<EpisodeDetails>>

    fun getLocations(): LiveData<PagingData<LocationDetails>>

    fun getFilteredLocations(
        locationFilter: LocationFilter
    ): LiveData<PagingData<LocationDetails>>

    suspend fun getMultipleCharactersInLocation(
        locationId: Int,
        charactersIds: String
    ): List<CharacterDetails>

    suspend fun getLocationById(id: Int): LocationDetails?
}