package com.example.jsonplaceholder.view

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomeViewPagerAdapter(fragment: HomeFragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PostListFragment()
            else -> AlbumListFragment()
        }
    }
}