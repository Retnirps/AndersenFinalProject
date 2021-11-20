package com.majestadev.rickandmortyguide.main.di.modules

import android.content.Context
import androidx.room.Room
import com.majestadev.data.cache.Cache
import com.majestadev.data.cache.database.RickAndMortyDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule {

    @Singleton
    @Provides
    fun cache(context: Context): Cache {
        val database =
            Room.databaseBuilder(context, RickAndMortyDatabase::class.java, DATABASE_NAME).build()
        return Cache(database.rickAndMortyDao)
    }

    private companion object {
        const val DATABASE_NAME = "rick_and_morty_database"
    }
}