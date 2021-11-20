package com.majestadev.rickandmortyguide.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.majestadev.rickandmortyguide.main.fragment.characters.CharactersFragment
import com.majestadev.rickandmortyguide.main.fragment.episodes.EpisodesFragment
import com.majestadev.rickandmortyguide.main.fragment.locations.LocationsFragment

private const val NUM_PAGES = 3

class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CharactersFragment()
            1 -> EpisodesFragment()
            2 -> LocationsFragment()
            else -> CharactersFragment()
        }
    }
}