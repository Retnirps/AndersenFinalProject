package com.majestadev.rickandmortyguide.main.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.majestadev.domain.dto.CharacterFilter
import com.majestadev.domain.interactors.characters.ICharactersInteractor
import com.majestadev.rickandmortyguide.main.di.Injector
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    val charactersInteractor: ICharactersInteractor
) : ViewModel() {

    fun getCharacters() = charactersInteractor.getCharacters().cachedIn(viewModelScope)

    fun getFilteredCharacters(charactersFilter: CharacterFilter) =
        charactersInteractor.getFilteredCharacters(charactersFilter).cachedIn(viewModelScope)

    override fun onCleared() {
        super.onCleared()
        Injector.clearCharactersFragmentComponent()
    }
}