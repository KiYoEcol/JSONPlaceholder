package com.example.jsonplaceholder.model

data class CommentModel(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)
