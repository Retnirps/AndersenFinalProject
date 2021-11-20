package com.majestadev.rickandmortyguide.main.fragment.main

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.majestadev.rickandmortyguide.R
import com.majestadev.rickandmortyguide.databinding.FragmentMainBinding
import com.majestadev.rickandmortyguide.main.ScreenSlidePagerAdapter

class MainFragment : Fragment() {
    private var binding: FragmentMainBinding? = null
    private var viewPagerListener: ViewPagerListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        val view = binding?.root

        setHasOptionsMenu(true)

        val pagerAdapter = ScreenSlidePagerAdapter(requireActivity())
        binding?.viewPager2?.adapter = pagerAdapter

        binding?.viewPager2?.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                when (position) {
                    0 -> {
                        viewPagerListener?.pageChanged(R.string.characters)
                    }
                    1 -> {
                        viewPagerListener?.pageChanged(R.string.episodes)
                    }
                    2 -> {
                        viewPagerListener?.pageChanged(R.string.locations)
                    }
                }
            }
        })

        binding?.let {
            TabLayoutMediator(it.tabLayout, it.viewPager2) { tab, position ->
                when (position) {
                    0 -> {
                        tab.setIcon(R.drawable.ic_characters)
                    }
                    1 -> {
                        tab.setIcon(R.drawable.ic_episodes)
                    }
                    2 -> {
                        tab.setIcon(R.drawable.ic_locations)
                    }
                }
            }.attach()
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is ViewPagerListener) {
            viewPagerListener = context
        } else {
            throw ClassCastException("$context must implement ViewPagerListener")
        }
    }
}