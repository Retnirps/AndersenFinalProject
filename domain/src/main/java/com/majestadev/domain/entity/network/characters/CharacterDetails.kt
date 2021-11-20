package com.majestadev.domain.entity.network.characters

import com.google.gson.annotations.SerializedName
import com.majestadev.domain.entity.db.CharacterEntity
import com.majestadev.domain.util.RegexUtil

data class CharacterDetails(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("species")
    val species: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("origin")
    val origin: Place,
    @SerializedName("location")
    val location: Place,
    @SerializedName("image")
    val image: String,
    @SerializedName("episode")
    val episode: List<String>
) {
    fun toCharacterEntity() =
        CharacterEntity(
            id,
            name,
            status,
            species,
            type,
            gender,
            RegexUtil.extractIdFromUrl(origin.url),
            RegexUtil.extractIdFromUrl(location.url),
            image
        )
}


