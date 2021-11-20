package com.majestadev.rickandmortyguide.main.fragment.episodes.details

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.majestadev.domain.entity.network.characters.CharacterDetails
import com.majestadev.rickandmortyguide.R
import com.majestadev.rickandmortyguide.databinding.EpisodeCharacterItemBinding

class EpisodeDetailsRecyclerViewAdapter(private val onClick: (CharacterDetails) -> Unit) :
    RecyclerView.Adapter<EpisodeDetailsRecyclerViewAdapter.ViewHolder>() {
    private var charactersList: List<CharacterDetails> = emptyList()

    class ViewHolder(private val binding: EpisodeCharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(characterDetails: CharacterDetails) {
            binding.nameTextView.text = characterDetails.name
            binding.statusTextView.text = characterDetails.status
            binding.speciesTextView.text = characterDetails.species
            binding.genderTextView.text = characterDetails.gender
            Glide
                .with(binding.root.context)
                .load(characterDetails.image)
                .placeholder(
                    ContextCompat.getDrawable(binding.root.context, R.drawable.transparent)
                )
                .into(binding.characterImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            EpisodeCharacterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = charactersList[position]

        holder.itemView.setOnClickListener { onClick(item) }

        holder.bind(item)

        applyScaleAnimation(holder.itemView)
    }

    override fun getItemCount(): Int {
        return charactersList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(charactersList: List<CharacterDetails>) {
        this.charactersList = charactersList
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
        anim.duration = 125
        view.startAnimation(anim)
    }
}