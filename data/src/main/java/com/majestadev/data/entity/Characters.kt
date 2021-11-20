package com.majestadev.data.entity

import com.google.gson.annotations.SerializedName
import com.majestadev.domain.entity.network.characters.CharacterDetails

data class Characters(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val results: List<CharacterDetails>
)