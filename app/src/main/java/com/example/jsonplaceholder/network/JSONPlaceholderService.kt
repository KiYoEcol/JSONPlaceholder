package com.example.jsonplaceholder.network

import com.example.jsonplaceholder.model.AlbumModel
import com.example.jsonplaceholder.model.PostModel
import retrofit2.Response
import retrofit2.http.GET

interface JSONPlaceholderService {
    @GET("posts")
    suspend fun getPosts(): Response<List<PostModel>>

    @GET("albums")
    suspend fun getAlbums():Response<List<AlbumModel>>
}