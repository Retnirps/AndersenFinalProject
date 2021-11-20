package com.majestadev.domain.entity.network.locations

import com.google.gson.annotations.SerializedName
import com.majestadev.domain.entity.db.LocationEntity

data class LocationDetails(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("dimension")
    val dimension: String,
    @SerializedName("residents")
    val residents: List<String>
) {
    fun toLocationEntity() =
        LocationEntity(
            id,
            name,
            type,
            dimension
        )
}
