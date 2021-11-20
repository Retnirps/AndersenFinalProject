package com.majestadev.data.cache.database

import androidx.room.*
import com.majestadev.domain.entity.db.*

@Dao
interface RickAndMortyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCharacter(character: CharacterEntity)

    @Query("select * from characters order by characterId asc")
    suspend fun getCharacters(): List<CharacterEntity>

    @Query("select * from characters where name like ifnull(:name, '%%') and status like ifnull(:status, '%%') and species like ifnull(:species, '%%') and type like ifnull(:type, '%%') and gender like ifnull(:gender, '%%') order by characterId asc")
    suspend fun getFilteredCharacters(
        name: String?,
        status: String?,
        species: String?,
        type: String?,
        gender: String?
    ): List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEpisode(episode: EpisodeEntity)

    @Query("select * from episodes order by episodeId asc")
    suspend fun getEpisodes(): List<EpisodeEntity>

    @Query("select * from episodes where name like ifnull(:name, '%%') and episode like ifnull(:episode, '%%') order by episodeId asc")
    suspend fun getFilteredEpisodes(
        name: String?,
        episode: String?
    ): List<EpisodeEntity>

    @Query("select * from episodes where episodeId = :id")
    suspend fun getEpisodeById(id: Int): EpisodeEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCharacterEpisodeCrossRef(characterEpisodeCrossRef: CharacterEpisodeCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLocation(location: LocationEntity)

    @Query("select * from locations order by id asc")
    suspend fun getLocations(): List<LocationEntity>

    @Query("select * from locations where id = :id")
    suspend fun getLocationById(id: Int): LocationEntity

    @Query("select * from locations where name like ifnull(:name, '%%') and type like ifnull(:type, '%%') and dimension like ifnull(:dimension, '%%') order by id asc")
    suspend fun getFilteredLocations(
        name: String?,
        type: String?,
        dimension: String?
    ): List<LocationEntity>

    @Transaction
    @Query("select * from characters where characterId = :id")
    suspend fun getCharacterWithEpisodes(id: Int): CharacterWithEpisodes

    @Transaction
    @Query("select * from episodes where episodeId = :id")
    suspend fun getEpisodeWithCharacters(id: Int): EpisodeWithCharacters

    @Transaction
    @Query("select * from characters where characterId = :id")
    suspend fun getCharacterWithLocation(id: Int): CharacterWithOriginWithLocation

    @Transaction
    @Query("select * from locations where id = :id")
    suspend fun getLocationWithResidents(id: Int): LocationWithResidents
}