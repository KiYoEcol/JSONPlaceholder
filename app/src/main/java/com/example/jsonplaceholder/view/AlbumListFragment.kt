package com.example.jsonplaceholder.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jsonplaceholder.databinding.FragmentListBinding
import com.example.jsonplaceholder.model.AlbumModel
import com.example.jsonplaceholder.viewmodel.AlbumListViewModel

class AlbumListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private val viewModel: AlbumListViewModel by viewModels()
    private lateinit var albumListAdapter: AlbumListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        albumListAdapter = AlbumListAdapter(viewLifecycleOwner) { onClickAlbum(it) }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = albumListAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.albums.observe(viewLifecycleOwner) {
            albumListAdapter.submitList(it)
        }
        viewModel.showErrorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        val userId = arguments?.getInt("userId", -1) ?: -1
        if (userId == -1) viewModel.getAlbums() else viewModel.getAlbumsOnUser(userId)
    }

    private fun onClickAlbum(album: AlbumModel) {
        val userId = arguments?.getInt("userId", -1) ?: -1
        val action = if (userId == -1) {
            HomeFragmentDirections.actionHomeFragmentToPhotoListFragment(
                userId = album.userId,
                albumId = album.id
            )
        } else {
            UserFragmentDirections.actionUserFragmentToPhotoListFragment(
                userId = userId,
                albumId = album.id
            )
        }
        findNavController().navigate(action)
    }
}