package com.example.jsonplaceholder.repository

import com.example.jsonplaceholder.network.JSONPlaceholderNetwork
import com.example.jsonplaceholder.model.PostModel

class JSONPlaceholderRepository {
    suspend fun getPosts(): List<PostModel> {
        return JSONPlaceholderNetwork.service.getPosts()
    }
}