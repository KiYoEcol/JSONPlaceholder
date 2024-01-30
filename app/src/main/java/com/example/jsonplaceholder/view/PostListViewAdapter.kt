package com.example.jsonplaceholder.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.jsonplaceholder.databinding.ItemPostBinding
import com.example.jsonplaceholder.model.PostModel

class PostListViewAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val posts: List<PostModel>
) : RecyclerView.Adapter<PostListViewAdapter.PostListViewHolder>() {
    class PostListViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: PostModel, lifecycleOwner: LifecycleOwner) {
            binding.lifecycleOwner = lifecycleOwner
            binding.post = post
            binding.cardView.setOnClickListener {  }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPostBinding.inflate(inflater, parent, false)
        return PostListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: PostListViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post, lifecycleOwner)
    }
}