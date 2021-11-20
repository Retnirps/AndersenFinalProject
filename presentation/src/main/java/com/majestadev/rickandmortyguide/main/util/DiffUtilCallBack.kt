package com.majestadev.rickandmortyguide.main.util

import androidx.recyclerview.widget.DiffUtil
import com.majestadev.domain.entity.network.characters.CharacterDetails
import com.majestadev.domain.entity.network.episodes.EpisodeDetails
import com.majestadev.domain.entity.network.locations.LocationDetails

class DiffUtilCallBack<T> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return when (oldItem) {
            is CharacterDetails -> {
                (oldItem as CharacterDetails).id == (newItem as CharacterDetails).id
            }
            is EpisodeDetails -> {
                (oldItem as EpisodeDetails).id == (newItem as EpisodeDetails).id
            }
            is LocationDetails -> {
                (oldItem as LocationDetails).id == (newItem as LocationDetails).id
            }
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return when (oldItem) {
            is CharacterDetails -> {
                oldItem as CharacterDetails == newItem as CharacterDetails
            }
            is EpisodeDetails -> {
                oldItem as EpisodeDetails == newItem as EpisodeDetails
            }
            is LocationDetails -> {
                oldItem as LocationDetails == newItem as LocationDetails
            }
            else -> false
        }
    }
}