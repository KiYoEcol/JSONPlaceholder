package com.example.jsonplaceholder.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jsonplaceholder.databinding.ItemAlumBinding
import com.example.jsonplaceholder.model.AlbumModel

private object DiffCallback : DiffUtil.ItemCallback<AlbumModel>() {
    override fun areItemsTheSame(oldItem: AlbumModel, newItem: AlbumModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AlbumModel, newItem: AlbumModel): Boolean {
        return oldItem == newItem
    }
}

class AlbumListAdapter(private val lifecycleOwner: LifecycleOwner) :
    ListAdapter<AlbumModel, AlbumListAdapter.AlbumListViewHolder>(DiffCallback) {
    class AlbumListViewHolder(private val binding: ItemAlumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(lifecycleOwner: LifecycleOwner, model: AlbumModel) {
            binding.lifecycleOwner = lifecycleOwner
            binding.model = model
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAlumBinding.inflate(inflater, parent, false)
        return AlbumListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumListViewHolder, position: Int) {
        holder.bind(lifecycleOwner, getItem(position))
    }
}