package com.example.jsonplaceholder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class ListViewModel: ViewModel() {
    protected val _isProceeding = MutableLiveData<Boolean>(true)
    val isProceeding: LiveData<Boolean> = _isProceeding
    protected val _showErrorMessage = MutableLiveData<String>()
    val showErrorMessage: LiveData<String> = _showErrorMessage
}