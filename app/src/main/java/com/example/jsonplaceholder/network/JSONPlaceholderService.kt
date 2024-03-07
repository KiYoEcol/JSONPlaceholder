package com.example.jsonplaceholder.network

import com.example.jsonplaceholder.model.PostModel
import retrofit2.Response
import retrofit2.http.GET

interface JSONPlaceholderService {
    @GET("posts")
    suspend fun getPosts(): Response<List<PostModel>>
}