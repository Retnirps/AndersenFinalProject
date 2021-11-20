package com.majestadev.rickandmortyguide.main.view.di.characters.details

import androidx.lifecycle.ViewModel
import com.majestadev.rickandmortyguide.main.di.ViewModelKey
import com.majestadev.rickandmortyguide.main.view.viewmodel.details.CharacterDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CharacterDetailsFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(CharacterDetailsViewModel::class)
    fun bindCharacterDetailsViewModel(characterDetailsViewModel: CharacterDetailsViewModel): ViewModel
}