package com.example.jsonplaceholder.view

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {
    @BindingAdapter("load_image_url")
    @JvmStatic
    fun loadImageUrl(imageView: ImageView, url: String?){
        Glide.with(imageView)
            .load(url)
            .into(imageView)
    }
}