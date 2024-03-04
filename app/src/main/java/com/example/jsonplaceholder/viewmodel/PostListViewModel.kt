package com.example.jsonplaceholder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jsonplaceholder.model.PostModel
import com.example.jsonplaceholder.repository.JSONPlaceholderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostListViewModel : ViewModel() {
    private val repository = JSONPlaceholderRepository()
    private val _posts = MutableLiveData<List<PostModel>>()
    val posts: LiveData<List<PostModel>> = _posts
    fun getPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            val posts = repository.getPosts()
            _posts.postValue(posts)
        }
    }
}