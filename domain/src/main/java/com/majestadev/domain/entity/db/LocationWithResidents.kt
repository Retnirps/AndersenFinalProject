package com.majestadev.domain.entity.db

import androidx.room.Embedded
import androidx.room.Relation

data class LocationWithResidents(
    @Embedded val location: LocationEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "locationId"
    )
    val residents: List<CharacterEntity>
)