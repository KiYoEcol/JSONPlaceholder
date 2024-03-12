package com.example.jsonplaceholder.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jsonplaceholder.databinding.ItemCommentBinding
import com.example.jsonplaceholder.model.CommentModel

private object CommentDiffCallback : DiffUtil.ItemCallback<CommentModel>() {
    override fun areItemsTheSame(oldItem: CommentModel, newItem: CommentModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CommentModel, newItem: CommentModel): Boolean {
        return oldItem == newItem
    }
}

class CommentListAdapter(val lifecycleOwner: LifecycleOwner) :
    ListAdapter<CommentModel, CommentListAdapter.CommentListViewHolder>(CommentDiffCallback) {
    class CommentListViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(lifecycleOwner: LifecycleOwner, comment: CommentModel) {
            binding.lifecycleOwner = lifecycleOwner
            binding.comment = comment
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCommentBinding.inflate(inflater, parent, false)
        return CommentListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentListViewHolder, position: Int) {
        holder.bind(lifecycleOwner, getItem(position))
    }
}