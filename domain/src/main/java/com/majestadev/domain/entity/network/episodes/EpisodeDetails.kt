package com.majestadev.domain.entity.network.episodes

import com.google.gson.annotations.SerializedName
import com.majestadev.domain.entity.db.EpisodeEntity

data class EpisodeDetails(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("episode")
    val episode: String,
    @SerializedName("characters")
    val characters: List<String>
) {
    fun toEpisodeEntity() =
        EpisodeEntity(
            id,
            name,
            airDate,
            episode
        )
}
