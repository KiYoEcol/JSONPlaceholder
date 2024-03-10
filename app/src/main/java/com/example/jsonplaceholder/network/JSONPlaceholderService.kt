package com.example.jsonplaceholder.network

import com.example.jsonplaceholder.model.AlbumModel
import com.example.jsonplaceholder.model.PostModel
import com.example.jsonplaceholder.model.UserModel
import retrofit2.Response
import retrofit2.http.GET

interface JSONPlaceholderService {
    @GET("posts")
    suspend fun getPosts(): Response<List<PostModel>>

    @GET("posts/{id}")
    suspend fun getPost(id: Int): Response<PostModel>

    @GET("albums")
    suspend fun getAlbums(): Response<List<AlbumModel>>

    @GET("user/{id}")
    suspend fun getUser(id: Int): Response<UserModel>
}