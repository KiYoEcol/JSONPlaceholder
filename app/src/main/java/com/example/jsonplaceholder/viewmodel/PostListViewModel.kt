package com.example.jsonplaceholder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jsonplaceholder.model.PostModel
import com.example.jsonplaceholder.network.Future
import com.example.jsonplaceholder.repository.JSONPlaceholderRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PostListViewModel : ViewModel() {
    private val repository = JSONPlaceholderRepository()
    private val _posts = MutableLiveData<Future<List<PostModel>>>()
    val posts: LiveData<Future<List<PostModel>>> = _posts
    fun getPosts() {
        viewModelScope.launch {
            repository.getPostsFlow().collectLatest {
                _posts.postValue(it)
            }
        }
    }
}