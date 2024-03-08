package com.example.jsonplaceholder.repository

import com.example.jsonplaceholder.model.AlbumModel
import com.example.jsonplaceholder.network.JSONPlaceholderNetwork
import com.example.jsonplaceholder.model.PostModel
import com.example.jsonplaceholder.network.Future
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import retrofit2.HttpException
import retrofit2.Response

class JSONPlaceholderRepository {
    private inline fun <reified T : Any> apiFlow(crossinline call: suspend () -> Response<T>): Flow<Future<T>> =
        flow<Future<T>> {
            val response = call()
            // 成功した場合は`Future.Success`に値をラップして出力
            if (response.isSuccessful) emit(Future.Success(value = response.body()!!))
            else throw HttpException(response)
        }.catch { it: Throwable ->
            // エラーが発生した場合は`Future.Error`に例外をラップして出力
            emit(Future.Error(it))
        }.onStart {
            // 起動時に`Future.Proceeding`を出力
            emit(Future.Proceeding)
        }.flowOn(Dispatchers.IO)

    suspend fun getPostsFlow(): Flow<Future<List<PostModel>>> {
        return apiFlow { JSONPlaceholderNetwork.service.getPosts() }
    }

    suspend fun getAlbumsFlow(): Flow<Future<List<AlbumModel>>> {
        return apiFlow { JSONPlaceholderNetwork.service.getAlbums() }
    }
}