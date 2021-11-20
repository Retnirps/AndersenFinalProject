package com.majestadev.rickandmortyguide.main.fragment.characters.details

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.recyclerview.widget.RecyclerView
import com.majestadev.domain.entity.network.episodes.EpisodeDetails
import com.majestadev.rickandmortyguide.databinding.EpisodeItemBinding

class CharacterDetailsRecyclerViewAdapter(private val onClick: (EpisodeDetails) -> Unit) :
    RecyclerView.Adapter<CharacterDetailsRecyclerViewAdapter.ViewHolder>() {
    private var episodesList: List<EpisodeDetails> = emptyList()

    class ViewHolder(private val binding: EpisodeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(episodesValue: EpisodeDetails) {
            binding.episodeNameTextView.text = episodesValue.name
            binding.episodeNumberTextView.text = episodesValue.episode
            binding.airDateTextView.text = episodesValue.airDate
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
        val item = episodesList[position]

        holder.itemView.setOnClickListener { onClick(item) }

        holder.bind(item)

        applyScaleAnimation(holder.itemView)
    }

    override fun getItemCount(): Int {
        return episodesList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(episodesList: List<EpisodeDetails>) {
        this.episodesList = episodesList
        notifyDataSetChanged()
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
        anim.duration = 1000
        view.startAnimation(anim)
    }
}