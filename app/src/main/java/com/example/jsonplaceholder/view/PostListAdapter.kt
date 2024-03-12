package com.example.jsonplaceholder.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jsonplaceholder.databinding.ItemPostBinding
import com.example.jsonplaceholder.model.PostModel

private object PostDiffCallback : DiffUtil.ItemCallback<PostModel>() {
    override fun areItemsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
        return oldItem == newItem
    }
}

class PostListAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val onClickPost: (PostModel) -> Unit
) : ListAdapter<PostModel, PostListAdapter.PostListViewHolder>(PostDiffCallback) {
    class PostListViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            lifecycleOwner: LifecycleOwner,
            post: PostModel,
            onClickPost: (PostModel) -> Unit,
        ) {
            binding.lifecycleOwner = lifecycleOwner
            binding.post = post
            binding.container.setOnClickListener {
                onClickPost.invoke(post)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPostBinding.inflate(inflater, parent, false)
        return PostListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostListViewHolder, position: Int) {
        holder.bind(lifecycleOwner, getItem(position), onClickPost)
    }
}