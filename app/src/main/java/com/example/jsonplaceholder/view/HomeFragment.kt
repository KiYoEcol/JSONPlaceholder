package com.example.jsonplaceholder.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.jsonplaceholder.R
import com.example.jsonplaceholder.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPagerHome.adapter = HomeViewPagerAdapter(this)
        TabLayoutMediator(binding.tabLayoutHome, binding.viewPagerHome) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.title_post_list)
                else -> tab.text = getString(R.string.title_album_list)
            }
        }.attach()
    }
}