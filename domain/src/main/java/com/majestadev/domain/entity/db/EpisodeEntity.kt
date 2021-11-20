package com.majestadev.domain.entity.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episodes")
data class EpisodeEntity(
    @PrimaryKey
    val episodeId: Int,
    val name: String,
    val airDate: String,
    val episode: String
)
