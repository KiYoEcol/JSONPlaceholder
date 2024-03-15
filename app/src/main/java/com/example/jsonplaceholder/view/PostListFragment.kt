package com.example.jsonplaceholder.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.jsonplaceholder.databinding.FragmentListBinding
import com.example.jsonplaceholder.model.PostModel
import com.example.jsonplaceholder.viewmodel.PostListViewModel

class PostListFragment : Fragment() {
    private val viewModel: PostListViewModel by viewModels()
    private lateinit var binding: FragmentListBinding
    private lateinit var postListAdapter: PostListAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        postListAdapter = PostListAdapter(viewLifecycleOwner) { onClickPost(it) }
        binding.recyclerView.apply {
            adapter = postListAdapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.posts.observe(viewLifecycleOwner) {
            postListAdapter.submitList(it)
        }
        viewModel.showErrorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        val userId = arguments?.getInt("userId", -1) ?: -1
        if (userId == -1) viewModel.getPosts() else viewModel.getPostsOnUser(userId)
    }

    private fun onClickPost(post: PostModel) {
        val userId = arguments?.getInt("userId", -1) ?: -1
        val action = if (userId == -1) {
            HomeFragmentDirections.actionHomeFragmentToPostFragment(post.id)
        } else {
            UserFragmentDirections.actionUserFragmentToPostFragment(post.id)
        }
        findNavController().navigate(action)
    }
}