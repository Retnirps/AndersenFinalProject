package com.majestadev.rickandmortyguide.main.di.modules

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.majestadev.data.repository.RickAndMortyRepository
import com.majestadev.domain.interactors.characters.CharactersInteractor
import com.majestadev.domain.interactors.characters.ICharactersInteractor
import com.majestadev.domain.interactors.episodes.EpisodesInteractor
import com.majestadev.domain.interactors.episodes.IEpisodesInteractor
import com.majestadev.domain.interactors.locations.ILocationsInteractor
import com.majestadev.domain.interactors.locations.LocationsInteractor
import com.majestadev.domain.repository.IRickAndMortyRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppModule.InnerModule::class])
class AppModule(private val context: Context) {

    @Provides
    fun provideContext() = context

    @Module
    interface InnerModule {
        @Binds
        @Singleton
        fun provideRepository(repository: RickAndMortyRepository): IRickAndMortyRepository

        @Binds
        fun provideCharactersInteractor(charactersInteractor: CharactersInteractor): ICharactersInteractor

        @Binds
        fun provideEpisodesInteractor(episodesInteractor: EpisodesInteractor): IEpisodesInteractor

        @Binds
        fun provideLocationsInteractor(locationsInteractor: LocationsInteractor): ILocationsInteractor

        @Binds
        fun provideViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
    }
}