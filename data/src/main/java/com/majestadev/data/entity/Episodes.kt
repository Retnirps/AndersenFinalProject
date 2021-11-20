package com.majestadev.data.entity

import com.google.gson.annotations.SerializedName
import com.majestadev.domain.entity.network.episodes.EpisodeDetails

data class Episodes(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val results: List<EpisodeDetails>
)