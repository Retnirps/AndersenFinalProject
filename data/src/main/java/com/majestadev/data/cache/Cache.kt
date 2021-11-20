package com.majestadev.data.cache

import com.majestadev.data.cache.database.RickAndMortyDao
import com.majestadev.domain.dto.CharacterFilter
import com.majestadev.domain.dto.EpisodeFilter
import com.majestadev.domain.dto.LocationFilter
import com.majestadev.domain.entity.db.CharacterEntity
import com.majestadev.domain.entity.db.CharacterEpisodeCrossRef
import com.majestadev.domain.entity.db.EpisodeEntity
import com.majestadev.domain.entity.db.LocationEntity

class Cache(private val rickAndMortyDao: RickAndMortyDao) {

    suspend fun getCharacters() = rickAndMortyDao.getCharacters()

    suspend fun getFilteredCharacters(characterFilter: CharacterFilter) =
        rickAndMortyDao.getFilteredCharacters(
            characterFilter.name,
            characterFilter.status,
            characterFilter.species,
            characterFilter.type,
            characterFilter.gender
        )

    suspend fun addCharacter(character: CharacterEntity) =
        rickAndMortyDao.addCharacter(character)

    suspend fun getEpisodes() = rickAndMortyDao.getEpisodes()

    suspend fun getFilteredEpisodes(episodeFilter: EpisodeFilter) =
        rickAndMortyDao.getFilteredEpisodes(
            episodeFilter.name,
            episodeFilter.episode
        )

    suspend fun addEpisode(episode: EpisodeEntity) =
        rickAndMortyDao.addEpisode(episode)

    suspend fun getEpisodeById(id: Int) =
        rickAndMortyDao.getEpisodeById(id)

    suspend fun addCharacterEpisodeCrossRef(characterEpisodeCrossRef: CharacterEpisodeCrossRef) =
        rickAndMortyDao.addCharacterEpisodeCrossRef(characterEpisodeCrossRef)

    suspend fun getLocations() = rickAndMortyDao.getLocations()

    suspend fun getLocationById(id: Int) = rickAndMortyDao.getLocationById(id)

    suspend fun getFilteredLocations(locationFilter: LocationFilter) =
        rickAndMortyDao.getFilteredLocations(
            locationFilter.name,
            locationFilter.type,
            locationFilter.dimension
        )

    suspend fun addLocation(location: LocationEntity) = rickAndMortyDao.addLocation(location)

    suspend fun getCharacterWithEpisodes(id: Int) = rickAndMortyDao.getCharacterWithEpisodes(id)

    suspend fun getEpisodeWithCharacters(id: Int) = rickAndMortyDao.getEpisodeWithCharacters(id)

    suspend fun getCharacterWithLocation(id: Int) = rickAndMortyDao.getCharacterWithLocation(id)

    suspend fun getLocationWithResidents(id: Int) = rickAndMortyDao.getLocationWithResidents(id)
}