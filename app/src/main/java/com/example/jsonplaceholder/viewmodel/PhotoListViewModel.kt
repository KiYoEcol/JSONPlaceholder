package com.example.jsonplaceholder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jsonplaceholder.model.AlbumModel
import com.example.jsonplaceholder.model.PhotoModel
import com.example.jsonplaceholder.model.UserModel
import com.example.jsonplaceholder.network.Future
import com.example.jsonplaceholder.repository.JSONPlaceholderRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PhotoListViewModel : ViewModel() {
    private val repository = JSONPlaceholderRepository()
    private val _user = MutableLiveData<UserModel>()
    val user: LiveData<UserModel> = _user
    private val _isProceedingUser = MutableLiveData<Boolean>()
    val isProceedingUser: LiveData<Boolean> = _isProceedingUser
    private val _album = MutableLiveData<AlbumModel>()
    val album: LiveData<AlbumModel> = _album
    private val _isProceedingAlbum = MutableLiveData<Boolean>()
    val isProceedingAlbum: LiveData<Boolean> = _isProceedingAlbum
    private val _photos = MutableLiveData<List<PhotoModel>>()
    val photos: LiveData<List<PhotoModel>> = _photos
    private val _isProceedingPhotos = MutableLiveData<Boolean>()
    val isProceedingPhotos: LiveData<Boolean> = _isProceedingPhotos
    private val _showErrorMessage = MutableLiveData<String>()
    val showErrorMessage: LiveData<String> = _showErrorMessage

    fun getUser(userId: Int) {
        viewModelScope.launch {
            repository.getUserFlow(userId).collectLatest {
                when (it) {
                    is Future.Proceeding -> _isProceedingUser.postValue(true)
                    is Future.Success -> {
                        _isProceedingUser.postValue(false)
                        this@PhotoListViewModel._user.postValue(it.value)
                    }

                    is Future.Error -> {
                        _isProceedingUser.postValue(false)
                        _showErrorMessage.postValue(it.error.message)
                    }
                }
            }
        }
    }

    fun getAlbum(albumId: Int) {
        viewModelScope.launch {
            repository.getAlbumFlow(albumId).collectLatest {
                when (it) {
                    is Future.Proceeding -> _isProceedingAlbum.postValue(true)
                    is Future.Success -> {
                        _isProceedingAlbum.postValue(false)
                        this@PhotoListViewModel._album.postValue(it.value)
                    }

                    is Future.Error -> {
                        _isProceedingAlbum.postValue(false)
                        _showErrorMessage.postValue(it.error.message)
                    }
                }
            }
        }
    }

    fun getPhotosOnAlbum(albumId: Int) {
        viewModelScope.launch {
            repository.getPhotosOnAlbum(albumId).collectLatest {
                when (it) {
                    is Future.Proceeding -> _isProceedingPhotos.postValue(true)
                    is Future.Success -> {
                        _isProceedingPhotos.postValue(false)
                        this@PhotoListViewModel._photos.postValue(it.value)
                    }

                    is Future.Error -> {
                        _isProceedingPhotos.postValue(false)
                        _showErrorMessage.postValue(it.error.message)
                    }
                }
            }
        }
    }
}