package com.majestadev.rickandmortyguide.main.fragment.locations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.majestadev.domain.entity.network.locations.LocationDetails
import com.majestadev.rickandmortyguide.databinding.LocationItemBinding
import com.majestadev.rickandmortyguide.main.util.DiffUtilCallBack

class LocationsRecyclerViewAdapter(private val onClick: (LocationDetails) -> Unit) :
    PagingDataAdapter<LocationDetails, LocationsRecyclerViewAdapter.ViewHolder>(DiffUtilCallBack()) {

    class ViewHolder(private val binding: LocationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(locationDetails: LocationDetails) {
            binding.locationNameTextView.text = locationDetails.name
            binding.typeTextView.text = locationDetails.type
            binding.dimensionTextView.text = locationDetails.dimension
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LocationItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        item?.let { locationDetails ->
            holder.itemView.setOnClickListener { onClick(locationDetails) }
            holder.bind(locationDetails)
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