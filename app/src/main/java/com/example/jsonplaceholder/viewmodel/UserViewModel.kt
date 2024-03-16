package com.example.jsonplaceholder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jsonplaceholder.model.UserModel
import com.example.jsonplaceholder.network.Future
import com.example.jsonplaceholder.repository.JSONPlaceholderRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val repository = JSONPlaceholderRepository()
    private val _user = MutableLiveData<UserModel>()
    val user: LiveData<UserModel> = _user
    private val _isProceeding = MutableLiveData<Boolean>()
    val isProceeding: LiveData<Boolean> = _isProceeding
    private val _showErrorMessage = MutableLiveData<String>()
    val showErrorMessage: LiveData<String> = _showErrorMessage

    fun getUser(userId: Int) {
        viewModelScope.launch {
            repository.getUserFlow(userId).collectLatest {
                when (it) {
                    is Future.Proceeding -> _isProceeding.postValue(true)
                    is Future.Success -> {
                        _isProceeding.postValue(false)
                        this@UserViewModel._user.postValue(it.value)
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