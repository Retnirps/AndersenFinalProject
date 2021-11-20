package com.majestadev.data.entity

import com.google.gson.annotations.SerializedName
import com.majestadev.domain.entity.network.locations.LocationDetails

data class Locations(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val results: List<LocationDetails>
)