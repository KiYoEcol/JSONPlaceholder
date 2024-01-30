package com.example.jsonplaceholder.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object JSONPlaceholderNetwork {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: JSONPlaceholderService = retrofit.create(JSONPlaceholderService::class.java)
}