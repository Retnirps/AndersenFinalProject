package com.majestadev.rickandmortyguide.main.application.di

import android.content.Context
import com.majestadev.rickandmortyguide.main.di.modules.AppModule
import com.majestadev.rickandmortyguide.main.di.modules.CacheModule
import com.majestadev.rickandmortyguide.main.di.modules.NetworkModule
import com.majestadev.rickandmortyguide.main.view.di.characters.CharactersFragmentComponent
import com.majestadev.rickandmortyguide.main.view.di.characters.details.CharacterDetailsFragmentComponent
import com.majestadev.rickandmortyguide.main.view.di.episodes.EpisodesFragmentComponent
import com.majestadev.rickandmortyguide.main.view.di.episodes.details.EpisodeDetailsFragmentComponent
import com.majestadev.rickandmortyguide.main.view.di.locations.LocationsFragmentComponent
import com.majestadev.rickandmortyguide.main.view.di.locations.details.LocationDetailsFragmentComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, CacheModule::class, NetworkModule::class])
interface ApplicationComponent {
    val context: Context
    val charactersFragmentComponent: CharactersFragmentComponent
    val episodesFragmentComponent: EpisodesFragmentComponent
    val locationsFragmentComponent: LocationsFragmentComponent
    val characterDetailsFragmentComponent: CharacterDetailsFragmentComponent
    val episodeDetailsFragmentComponent: EpisodeDetailsFragmentComponent
    val locationDetailsFragmentComponent: LocationDetailsFragmentComponent
}