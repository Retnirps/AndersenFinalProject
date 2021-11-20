package com.majestadev.rickandmortyguide.main.fragment.episodes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.majestadev.domain.entity.network.episodes.EpisodeDetails
import com.majestadev.rickandmortyguide.databinding.EpisodeItemBinding
import com.majestadev.rickandmortyguide.main.util.DiffUtilCallBack

class EpisodesRecyclerViewAdapter(private val onClick: (EpisodeDetails) -> Unit) :
    PagingDataAdapter<EpisodeDetails, EpisodesRecyclerViewAdapter.ViewHolder>(DiffUtilCallBack()) {

    class ViewHolder(private val binding: EpisodeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(episodeDetails: EpisodeDetails) {
            binding.episodeNameTextView.text = episodeDetails.name
            binding.episodeNumberTextView.text = episodeDetails.episode
            binding.airDateTextView.text = episodeDetails.airDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            EpisodeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        item?.let { episodeDetails ->
            holder.itemView.setOnClickListener { onClick(episodeDetails) }
            holder.bind(episodeDetails)
            applyScaleAnimation(holder.itemView)
        }
    }

    private fun applyScaleAnimation(view: View) {
        val anim = ScaleAnimation(
            0.0f,
            1.0f,
            0.0f,
            1.0f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        anim.duration = 125
        view.startAnimation(anim)
    }
}