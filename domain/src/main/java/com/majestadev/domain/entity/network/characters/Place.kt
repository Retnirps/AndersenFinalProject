package com.majestadev.domain.entity.network.characters

import com.google.gson.annotations.SerializedName

data class Place(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
