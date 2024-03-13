package com.example.jsonplaceholder.network

import com.example.jsonplaceholder.model.AlbumModel
import com.example.jsonplaceholder.model.CommentModel
import com.example.jsonplaceholder.model.PhotoModel
import com.example.jsonplaceholder.model.PostModel
import com.example.jsonplaceholder.model.UserModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JSONPlaceholderService {
    @GET("posts")
    suspend fun getPosts(): Response<List<PostModel>>

    @GET("posts/{id}")
    suspend fun getPost(@Path("id") id: Int): Response<PostModel>

    @GET("comments")
    suspend fun getCommentsOnPost(@Query("postId") postId: Int): Response<List<CommentModel>>

    @GET("albums")
    suspend fun getAlbums(): Response<List<AlbumModel>>

    @GET("photos")
    suspend fun getPhotosOnAlbum(@Query("albumId") albumId: Int): Response<List<PhotoModel>>

    @GET("photos/{id}")
    suspend fun getPhoto(@Path("id") id: Int): Response<PhotoModel>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): Response<UserModel>
}