package com.example.jsonplaceholder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jsonplaceholder.model.PhotoModel
import com.example.jsonplaceholder.model.UserModel
import com.example.jsonplaceholder.network.Future
import com.example.jsonplaceholder.repository.JSONPlaceholderRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PhotoViewModel : ViewModel() {
    private val repository = JSONPlaceholderRepository()
    private val _user = MutableLiveData<UserModel>()
    val user = _user
    private val _isProceedingUser = MutableLiveData<Boolean>()
    val isProceedingUser: LiveData<Boolean> = _isProceedingUser
    private val _photo = MutableLiveData<PhotoModel>()
    val photo = _photo
    private val _isProceedingPhoto = MutableLiveData<Boolean>()
    val isProceedingPhoto: LiveData<Boolean> = _isProceedingPhoto
    private val _showErrorMessage = MutableLiveData<String>()
    val showErrorMessage: LiveData<String> = _showErrorMessage

    fun getUser(userId: Int) {
        viewModelScope.launch {
            repository.getUserFlow(userId).collectLatest {
                when (it) {
                    is Future.Proceeding -> {
                        _isProceedingUser.postValue(true)
                    }

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

    fun getPhoto(photoId: Int) {
        viewModelScope.launch {
            repository.getPhotoFlow(photoId).collectLatest {
                when (it) {
                    is Future.Proceeding -> _isProceedingPhoto.postValue(true)
                    is Future.Success -> {
                        _isProceedingPhoto.postValue(false)
                        _photo.postValue(it.value)
                    }

                    is Future.Error -> {
                        _isProceedingPhoto.postValue(false)
                        _showErrorMessage.postValue(it.error.message)
                    }
                }
            }
        }
    }
}