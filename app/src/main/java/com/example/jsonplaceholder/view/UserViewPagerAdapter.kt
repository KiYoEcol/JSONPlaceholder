package com.example.jsonplaceholder.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class UserViewPagerAdapter(fragment: UserFragment, private val userId: Int) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        val args = Bundle().apply {
            putInt("userId", userId)
        }
        return when (position) {
            0 -> UserDetailFragment().apply {
                arguments = args
            }

            1 -> PostListFragment().apply {
                arguments = args
            }

            2 -> AlbumListFragment().apply {
                arguments = args
            }

            else -> TodoListFragment().apply {
                arguments = args
            }
        }
    }
}