package com.majestadev.rickandmortyguide.main.fragment.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.majestadev.domain.entity.network.characters.CharacterDetails
import com.majestadev.rickandmortyguide.R
import com.majestadev.rickandmortyguide.databinding.CharacterItemBinding
import com.majestadev.rickandmortyguide.main.util.DiffUtilCallBack
import android.view.animation.ScaleAnimation




class CharactersRecyclerViewAdapter(private val onClick: (CharacterDetails) -> Unit) :
    PagingDataAdapter<CharacterDetails, CharactersRecyclerViewAdapter.ViewHolder>(DiffUtilCallBack()) {

    class ViewHolder(private val binding: CharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(characterDetails: CharacterDetails) {
            binding.nameTextView.text = characterDetails.name
            binding.statusTextView.text = characterDetails.status
            binding.speciesTextView.text = characterDetails.species
            binding.genderTextView.text = characterDetails.gender
            Glide
                .with(binding.root.context)
                .load(characterDetails.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(
                    ContextCompat.getDrawable(binding.root.context, R.drawable.transparent)
                )
                .into(binding.characterImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CharacterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        item?.let { characterDetails ->
            holder.itemView.setOnClickListener { onClick(characterDetails) }
            holder.bind(characterDetails)
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