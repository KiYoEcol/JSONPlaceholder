package com.example.jsonplaceholder.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.jsonplaceholder.databinding.FragmentPostListBinding
import com.example.jsonplaceholder.network.Future
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
        viewModel.posts.observe(viewLifecycleOwner) {
            binding.recyclerViewPostList.apply {
                when (it) {
                    is Future.Proceeding -> {
                        binding.containerProgress.visibility = View.VISIBLE
                    }

                    is Future.Success -> {
                        adapter = PostListViewAdapter(viewLifecycleOwner, it.value)
                        layoutManager =
                            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                        binding.containerProgress.visibility = View.GONE
                    }

                    is Future.Error -> {
                        binding.containerProgress.visibility = View.GONE
                        Toast.makeText(context, it.error.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        viewModel.getPosts()
    }
}