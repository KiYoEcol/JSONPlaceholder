package com.example.jsonplaceholder.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.jsonplaceholder.databinding.FragmentPostListBinding
import com.example.jsonplaceholder.viewmodel.PostListViewModel

class PostListFragment : Fragment() {
    private val viewModel: PostListViewModel by viewModels()
    private lateinit var binding: FragmentPostListBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPosts()
        viewModel.posts.observe(viewLifecycleOwner) {
            binding.recyclerViewPostList.apply {
                adapter = PostListViewAdapter(viewLifecycleOwner, it)
                layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            }
        }
    }
}