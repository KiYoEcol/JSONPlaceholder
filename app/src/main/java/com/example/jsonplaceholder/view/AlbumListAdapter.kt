package com.example.jsonplaceholder.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jsonplaceholder.databinding.ItemAlbumBinding
import com.example.jsonplaceholder.model.AlbumModel

private object AlbumDiffCallback : DiffUtil.ItemCallback<AlbumModel>() {
    override fun areItemsTheSame(oldItem: AlbumModel, newItem: AlbumModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AlbumModel, newItem: AlbumModel): Boolean {
        return oldItem == newItem
    }
}

class AlbumListAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val onClickAlbum: (AlbumModel) -> Unit
) :
    ListAdapter<AlbumModel, AlbumListAdapter.AlbumListViewHolder>(AlbumDiffCallback) {
    class AlbumListViewHolder(private val binding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            lifecycleOwner: LifecycleOwner,
            album: AlbumModel,
            onClickAlbum: (AlbumModel) -> Unit
        ) {
            binding.lifecycleOwner = lifecycleOwner
            binding.album = album
            binding.container.setOnClickListener { onClickAlbum.invoke(album) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAlbumBinding.inflate(inflater, parent, false)
        return AlbumListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumListViewHolder, position: Int) {
        holder.bind(lifecycleOwner, getItem(position), onClickAlbum)
    }
}