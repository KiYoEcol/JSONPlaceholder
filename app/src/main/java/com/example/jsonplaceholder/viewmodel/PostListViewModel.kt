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

class PostListViewModel : ListViewModel() {
    private val repository = JSONPlaceholderRepository()
    private val _posts = MutableLiveData<List<PostModel>>()
    val posts: LiveData<List<PostModel>> = _posts
    private val _showErrorMessage = MutableLiveData<String>()
    val showErrorMessage: LiveData<String> = _showErrorMessage
    fun getPosts() {
        viewModelScope.launch {
            repository.getPostsFlow().collectLatest {
                when (it) {
                    is Future.Proceeding -> {
                        _isProceeding.postValue(true)
                    }

                    is Future.Success -> {
                        _isProceeding.postValue(false)
                        _posts.postValue(it.value)
                    }

                    is Future.Error -> {
                        _isProceeding.postValue(false)
                        _showErrorMessage.postValue(it.error.message)
                    }
                }
            }
        }
    }
}