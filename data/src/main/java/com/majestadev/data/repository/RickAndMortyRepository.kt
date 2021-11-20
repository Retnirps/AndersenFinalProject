package com.majestadev.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.majestadev.data.cache.Cache
import com.majestadev.data.network.Network
import com.majestadev.data.repository.paging.characters.CharactersPagingSource
import com.majestadev.data.repository.paging.characters.FilteredCharactersPagingSource
import com.majestadev.data.repository.paging.episodes.EpisodesPagingSource
import com.majestadev.data.repository.paging.episodes.FilteredEpisodesPagingSource
import com.majestadev.data.repository.paging.locations.FilteredLocationsPagingSource
import com.majestadev.data.repository.paging.locations.LocationsPagingSource
import com.majestadev.domain.dto.CharacterFilter
import com.majestadev.domain.dto.EpisodeFilter
import com.majestadev.domain.dto.LocationFilter
import com.majestadev.domain.entity.network.characters.CharacterDetails
import com.majestadev.domain.entity.network.characters.Place
import com.majestadev.domain.entity.network.episodes.EpisodeDetails
import com.majestadev.domain.entity.network.locations.LocationDetails
import com.majestadev.domain.repository.IRickAndMortyRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RickAndMortyRepository @Inject constructor(
    private val cache: Cache,
    private val network: Network
) : IRickAndMortyRepository {

    override fun getCharacters() = Pager(
        config = PagingConfig(enablePlaceholders = false, pageSize = 20),
        pagingSourceFactory = { CharactersPagingSource(cache, network) }
    ).liveData

    override suspend fun getMultipleCharactersInEpisode(
        episodeId: Int,
        charactersIds: String
    ): List<CharacterDetails> {
        var characters = emptyList<CharacterDetails>()

        try {
            val response = network.getMultipleCharacters(charactersIds)

            response.body()?.let {
                characters = it
            }
        } catch (e: Exception) {
            val episodeWithCharacters = cache.getEpisodeWithCharacters(episodeId)
            characters = episodeWithCharacters.characters.map {
                CharacterDetails(
                    it.characterId,
                    it.name,
                    it.status,
                    it.species,
                    it.type,
                    it.gender,
                    Place("", ""),
                    Place("", ""),
                    it.image,
                    emptyList()
                )
            }
        }

        return characters
    }

    override fun getFilteredCharacters(characterFilter: CharacterFilter) = Pager(
        config = PagingConfig(enablePlaceholders = false, pageSize = 20),
        pagingSourceFactory = { FilteredCharactersPagingSource(characterFilter, cache, network) }
    ).liveData

    override suspend fun getCharacterById(id: Int): CharacterDetails? {
        var characterDetails: CharacterDetails? = null

        try {
            val response = network.getCharacterById(id)

            response.body()?.let {
                characterDetails = it
            }
        } catch (e: Exception) {
            val characterEntity = cache.getCharacterWithLocation(id)

            val character = characterEntity.character
            val origin = characterEntity.origin
            val location = characterEntity.location

            characterDetails = CharacterDetails(
                character.characterId,
                character.name,
                character.status,
                character.species,
                character.type,
                character.gender,
                if (origin == null) Place("unknown", "") else Place(
                    origin.name,
                    origin.id.toString()
                ),
                if (location == null) Place("unknown", "") else Place(
                    location.name,
                    location.id.toString()
                ),
                character.image,
                emptyList()
            )
        }

        return characterDetails
    }

    override fun getEpisodes() = Pager(
        config = PagingConfig(enablePlaceholders = false, pageSize = 20),
        pagingSourceFactory = { EpisodesPagingSource(cache, network) }
    ).liveData

    override suspend fun getMultipleEpisodes(
        characterId: Int,
        episodesIds: String
    ): List<EpisodeDetails> {
        var episodes = emptyList<EpisodeDetails>()

        try {
            val response = network.getMultipleEpisodes(episodesIds)

            response.body()?.let {
                episodes = it
            }
        } catch (e: Exception) {
            val characterWithEpisodes = cache.getCharacterWithEpisodes(characterId)
            episodes = characterWithEpisodes.episodes.map {
                EpisodeDetails(
                    it.episodeId,
                    it.name,
                    it.airDate,
                    it.episode,
                    emptyList()
                )
            }
        }

        return episodes
    }

    override suspend fun getEpisodeById(id: Int): EpisodeDetails? {
        var episodeDetails: EpisodeDetails? = null

        try {
            val response = network.getEpisodeById(id)

            response.body()?.let {
                episodeDetails = it
            }
        } catch (e: Exception) {
            val episodeEntity = cache.getEpisodeById(id)

            episodeDetails = EpisodeDetails(
                episodeEntity.episodeId,
                episodeEntity.name,
                episodeEntity.airDate,
                episodeEntity.episode,
                emptyList()
            )
        }

        return episodeDetails
    }

    override fun getFilteredEpisodes(episodeFilter: EpisodeFilter) = Pager(
        config = PagingConfig(enablePlaceholders = false, pageSize = 20),
        pagingSourceFactory = { FilteredEpisodesPagingSource(episodeFilter, cache, network) }
    ).liveData

    override fun getLocations() = Pager(
        config = PagingConfig(enablePlaceholders = false, pageSize = 20),
        pagingSourceFactory = { LocationsPagingSource(cache, network) }
    ).liveData

    override fun getFilteredLocations(locationFilter: LocationFilter) = Pager(
        config = PagingConfig(enablePlaceholders = false, pageSize = 20),
        pagingSourceFactory = { FilteredLocationsPagingSource(locationFilter, cache, network) }
    ).liveData

    override suspend fun getMultipleCharactersInLocation(
        locationId: Int,
        charactersIds: String
    ): List<CharacterDetails> {
        var characters = emptyList<CharacterDetails>()

        try {
            val response = network.getMultipleCharacters(charactersIds)

            response.body()?.let {
                characters = it
            }
        } catch (e: Exception) {
            val locationWithResidents = cache.getLocationWithResidents(locationId)
            characters = locationWithResidents.residents.map {
                CharacterDetails(
                    it.characterId,
                    it.name,
                    it.status,
                    it.species,
                    it.type,
                    it.gender,
                    Place("", ""),
                    Place("", ""),
                    it.image,
                    emptyList()
                )
            }
        }

        return characters
    }

    override suspend fun getLocationById(id: Int): LocationDetails? {
        var locationDetails: LocationDetails? = null

        try {
            val response = network.getLocationById(id)

            response.body()?.let {
                locationDetails = it
            }
        } catch (e: Exception) {
            val locationEntity = cache.getLocationById(id)

            locationDetails = LocationDetails(
                locationEntity.id,
                locationEntity.name,
                locationEntity.type,
                locationEntity.dimension,
                emptyList()
            )
        }

        return locationDetails
    }
}