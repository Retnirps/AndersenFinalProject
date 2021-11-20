package com.majestadev.domain.interactors.characters

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.majestadev.domain.dto.CharacterFilter
import com.majestadev.domain.entity.network.characters.CharacterDetails

interface ICharactersInteractor {

    fun getCharacters(): LiveData<PagingData<CharacterDetails>>

    fun getFilteredCharacters(characterFilter: CharacterFilter): LiveData<PagingData<CharacterDetails>>

    suspend fun getMultipleCharactersInEpisode(
        episodeId: Int,
        charactersIds: String
    ): List<CharacterDetails>

    suspend fun getMultipleCharactersInLocation(
        locationId: Int,
        residentsIds: String
    ): List<CharacterDetails>

    suspend fun getCharacterById(id: Int): CharacterDetails?
}