package com.example.jsonplaceholder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jsonplaceholder.model.CommentModel
import com.example.jsonplaceholder.model.PostModel
import com.example.jsonplaceholder.model.UserModel
import com.example.jsonplaceholder.network.Future
import com.example.jsonplaceholder.repository.JSONPlaceholderRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {
    private val repository = JSONPlaceholderRepository()
    private val _user = MutableLiveData<UserModel>()
    val user: LiveData<UserModel> = _user
    private val _isProceedingUser = MutableLiveData<Boolean>()
    val isProceedingUser: LiveData<Boolean> = _isProceedingUser
    private val _post = MutableLiveData<PostModel>()
    val post = _post
    private val _isProceedingPost = MutableLiveData<Boolean>()
    val isProceedingPost: LiveData<Boolean> = _isProceedingPost
    private val _comments = MutableLiveData<List<CommentModel>>()
    val comments = _comments
    private val _isProceedingComments = MutableLiveData<Boolean>()
    val isProceedingComments: LiveData<Boolean> = _isProceedingComments
    private val _showErrorMessage = MutableLiveData<String>()
    val showErrorMessage: LiveData<String> = _showErrorMessage

    fun fetch(postId: Int) {
        viewModelScope.launch {
            repository.getPostFlow(postId).collectLatest {
                when (it) {
                    is Future.Proceeding -> _isProceedingPost.postValue(true)
                    is Future.Success -> {
                        _isProceedingPost.postValue(false)
                        _post.postValue(it.value)
                        getUser(it.value.id)
                    }

                    is Future.Error -> {
                        _isProceedingPost.postValue(false)
                        _showErrorMessage.postValue(it.error.message)
                    }
                }
            }
            getComments(postId)
        }
    }

    fun getUser(userId: Int) {
        viewModelScope.launch {
            repository.getUserFlow(userId).collectLatest {
                when (it) {
                    is Future.Proceeding -> _isProceedingUser.postValue(true)
                    is Future.Success -> {
                        _isProceedingUser.postValue(false)
                        _user.postValue(it.value)
                    }

                    is Future.Error -> {
                        _isProceedingUser.postValue(false)
                        _showErrorMessage.postValue(it.error.message)
                    }
                }
            }
        }
    }

    fun getComments(postId: Int) {
        viewModelScope.launch {
            repository.getCommentsOnPostFlow(postId).collectLatest {
                when (it) {
                    is Future.Proceeding -> _isProceedingComments.postValue(true)
                    is Future.Success -> {
                        _isProceedingComments.postValue(false)
                        _comments.postValue(it.value)
                    }

                    is Future.Error -> {
                        _isProceedingComments.postValue(false)
                        _showErrorMessage.postValue(it.error.message)
                    }
                }
            }
        }
    }
}