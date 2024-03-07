package com.example.jsonplaceholder.network

sealed class Future<out T> {
    // APIコールが実行中である
    object Proceeding : Future<Nothing>()

    // APIコールが成功した
    data class Success<out T>(val value: T) : Future<T>()

    // APIコールが失敗した
    data class Error(val error: Throwable) : Future<Nothing>()
}