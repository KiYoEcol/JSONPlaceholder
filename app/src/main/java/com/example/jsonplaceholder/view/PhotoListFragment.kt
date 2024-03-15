package com.example.jsonplaceholder.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jsonplaceholder.databinding.FragmentPhotoListBinding
import com.example.jsonplaceholder.model.PhotoModel
import com.example.jsonplaceholder.viewmodel.PhotoListViewModel

class PhotoListFragment : Fragment() {
    private lateinit var binding: FragmentPhotoListBinding
    private val viewModel: PhotoListViewModel by viewModels()
    private val args: PhotoListFragmentArgs by navArgs()
    private lateinit var photoListAdapter: PhotoListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        photoListAdapter = PhotoListAdapter(viewLifecycleOwner) { onClickPhoto(it) }
        binding.recyclerViewPhotoList.apply {
            layoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
            adapter = photoListAdapter
        }

        binding.containerUser.setOnClickListener {
            val userId = viewModel.user.value?.id ?: -1
            val action = PhotoListFragmentDirections.actionPhotoListFragmentToUserFragment(userId)
            findNavController().navigate(action)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.user.observe(viewLifecycleOwner) {
            binding.user = it
        }
        viewModel.isProceedingUser.observe(viewLifecycleOwner) {
            if (it) {
                binding.containerProgress.visibility = View.VISIBLE
            } else if (viewModel.isProceedingPhotos.value == false) {
                binding.containerProgress.visibility = View.GONE
            }
        }
        viewModel.photos.observe(viewLifecycleOwner) {
            photoListAdapter.submitList(it)
        }
        viewModel.isProceedingPhotos.observe(viewLifecycleOwner) {
            if (it) {
                binding.containerProgress.visibility = View.VISIBLE
            } else if (viewModel.isProceedingUser.value == false) {
                binding.containerProgress.visibility = View.GONE
            }
        }
        viewModel.showErrorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.getUser(args.userId)
        viewModel.getPhotosOnAlbum(args.albumId)
    }

    private fun onClickPhoto(photo: PhotoModel) {
        val userId = viewModel.user.value?.id ?: 0
        val action = PhotoListFragmentDirections.actionPhotoListFragmentToPhotoFragment(
            userId = userId,
            photoId = photo.id
        )
        findNavController().navigate(action)
    }
}