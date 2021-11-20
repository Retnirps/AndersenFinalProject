package com.majestadev.domain.interactors.characters

import com.majestadev.domain.dto.CharacterFilter
import com.majestadev.domain.repository.IRickAndMortyRepository
import javax.inject.Inject

class CharactersInteractor @Inject constructor(
    private val repository: IRickAndMortyRepository
) : ICharactersInteractor {

    override fun getCharacters() = repository.getCharacters()

    override fun getFilteredCharacters(characterFilter: CharacterFilter) =
        repository.getFilteredCharacters(characterFilter)

    override suspend fun getMultipleCharactersInEpisode(episodeId: Int, charactersIds: String) =
        repository.getMultipleCharactersInEpisode(episodeId, charactersIds)

    override suspend fun getMultipleCharactersInLocation(locationId: Int, residentsIds: String) =
        repository.getMultipleCharactersInLocation(locationId, residentsIds)

    override suspend fun getCharacterById(id: Int) = repository.getCharacterById(id)
}