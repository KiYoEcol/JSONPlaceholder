package com.example.jsonplaceholder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.jsonplaceholder.model.AlbumModel
import com.example.jsonplaceholder.network.Future
import com.example.jsonplaceholder.repository.JSONPlaceholderRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AlbumListViewModel : ListViewModel() {
    private val repository = JSONPlaceholderRepository()
    private val _albums = MutableLiveData<List<AlbumModel>>()
    val albums: LiveData<List<AlbumModel>> = _albums
    fun getAlbums() {
        viewModelScope.launch {
            repository.getAlbumsFlow().collectLatest {
                when (it) {
                    is Future.Proceeding -> _isProceeding.postValue(true)
                    is Future.Success -> {
                        _isProceeding.postValue(false)
                        _albums.postValue(it.value)
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