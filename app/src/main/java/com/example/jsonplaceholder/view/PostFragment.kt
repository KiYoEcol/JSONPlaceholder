package com.example.jsonplaceholder.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jsonplaceholder.databinding.FragmentPostBinding
import com.example.jsonplaceholder.viewmodel.PostViewModel

class PostFragment : Fragment() {
    private val viewModel: PostViewModel by viewModels()
    private lateinit var binding: FragmentPostBinding
    private val args: PostFragmentArgs by navArgs()
    private lateinit var commentListAdapter: CommentListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        commentListAdapter = CommentListAdapter(viewLifecycleOwner)
        binding.recyclerViewCommentList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = commentListAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isProceedingPost.observe(viewLifecycleOwner) {
            if (it) {
                binding.containerProgress.visibility = View.VISIBLE
            } else if (viewModel.isProceedingUser.value == false && viewModel.isProceedingComments.value == false) {
                binding.containerProgress.visibility = View.GONE
            }
        }
        viewModel.isProceedingUser.observe(viewLifecycleOwner) {
            if (it) {
                binding.containerProgress.visibility = View.VISIBLE
            } else if (viewModel.isProceedingPost.value == false && viewModel.isProceedingComments.value == false) {
                binding.containerProgress.visibility = View.GONE
            }
        }
        viewModel.user.observe(viewLifecycleOwner) {
            binding.user = it
        }
        viewModel.isProceedingComments.observe(viewLifecycleOwner) {
            if (it) {
                binding.containerProgress.visibility = View.VISIBLE
            } else if (viewModel.isProceedingPost.value == false && viewModel.isProceedingUser.value == false) {
                binding.containerProgress.visibility = View.GONE
            }
        }
        viewModel.comments.observe(viewLifecycleOwner) {
            commentListAdapter.submitList(it)
        }
        viewModel.showErrorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.fetch(args.postId)
    }
}