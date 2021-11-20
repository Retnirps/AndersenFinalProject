package com.majestadev.domain.entity.db

import androidx.room.Embedded
import androidx.room.Relation

data class CharacterWithOriginWithLocation(
    @Embedded val character: CharacterEntity,
    @Relation(
        parentColumn = "originId",
        entityColumn = "id"
    )
    val origin: LocationEntity?,
    @Relation(
        parentColumn = "locationId",
        entityColumn = "id"
    )
    val location: LocationEntity?
)
