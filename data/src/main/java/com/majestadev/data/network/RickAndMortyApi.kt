package com.majestadev.data.network

import com.majestadev.data.entity.Characters
import com.majestadev.data.entity.Episodes
import com.majestadev.data.entity.Locations
import com.majestadev.domain.entity.network.characters.CharacterDetails
import com.majestadev.domain.entity.network.episodes.EpisodeDetails
import com.majestadev.domain.entity.network.locations.LocationDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("character")
    suspend fun getCharacters(
        @Query("name") name: String?,
        @Query("status") status: String?,
        @Query("species") species: String?,
        @Query("type") type: String?,
        @Query("gender") gender: String?,
        @Query("page") page: Int
    ): Characters

    @GET("character/{charactersIds}")
    suspend fun getMultipleCharacters(@Path("charactersIds") charactersIds: String):
            Response<List<CharacterDetails>>

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): Response<CharacterDetails>

    @GET("episode")
    suspend fun getEpisodes(
        @Query("name") name: String?,
        @Query("episode") episode: String?,
        @Query("page") page: Int
    ): Episodes

    @GET("episode/{episodesIds}")
    suspend fun getMultipleEpisodes(@Path("episodesIds") episodesIds: String):
            Response<List<EpisodeDetails>>

    @GET("episode/{id}")
    suspend fun getEpisodeById(@Path("id") id: Int): Response<EpisodeDetails>

    @GET("location")
    suspend fun getLocations(
        @Query("name") name: String?,
        @Query("type") type: String?,
        @Query("dimension") dimension: String?,
        @Query("page") page: Int
    ): Locations

    @GET("location/{id}")
    suspend fun getLocationById(@Path("id") id: Int): Response<LocationDetails>
}