package com.majestadev.rickandmortyguide.main.view.di.characters

import androidx.lifecycle.ViewModel
import com.majestadev.rickandmortyguide.main.di.ViewModelKey
import com.majestadev.rickandmortyguide.main.view.viewmodel.CharactersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CharactersFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(CharactersViewModel::class)
    fun bindCharactersViewModel(charactersViewModel: CharactersViewModel): ViewModel
}