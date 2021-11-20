package com.majestadev.data.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.majestadev.domain.entity.db.CharacterEntity
import com.majestadev.domain.entity.db.CharacterEpisodeCrossRef
import com.majestadev.domain.entity.db.EpisodeEntity
import com.majestadev.domain.entity.db.LocationEntity

@Database(
    entities = [
        CharacterEntity::class,
        EpisodeEntity::class,
        LocationEntity::class,
        CharacterEpisodeCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
abstract class RickAndMortyDatabase : RoomDatabase() {
    abstract val rickAndMortyDao: RickAndMortyDao
}