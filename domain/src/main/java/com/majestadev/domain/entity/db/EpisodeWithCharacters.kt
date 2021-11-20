package com.majestadev.domain.entity.db

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class EpisodeWithCharacters(
    @Embedded val episode: EpisodeEntity,
    @Relation(
        parentColumn = "episodeId",
        entityColumn = "characterId",
        associateBy = Junction(CharacterEpisodeCrossRef::class)
    )
    val characters: List<CharacterEntity>
)