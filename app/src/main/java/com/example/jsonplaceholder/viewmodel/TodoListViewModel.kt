package com.example.jsonplaceholder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.jsonplaceholder.model.TodoModel
import com.example.jsonplaceholder.network.Future
import com.example.jsonplaceholder.repository.JSONPlaceholderRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TodoListViewModel : ListViewModel() {
    private val repository = JSONPlaceholderRepository()
    private val _todos = MutableLiveData<List<TodoModel>>()
    val todos: LiveData<List<TodoModel>> = _todos

    fun getTodosOnUser(userId: Int) {
        viewModelScope.launch {
            repository.getTodosOnUser(userId).collectLatest {
                when (it) {
                    is Future.Proceeding -> _isProceeding.postValue(true)
                    is Future.Success -> {
                        _isProceeding.postValue(false)
                        this@TodoListViewModel._todos.postValue(it.value)
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