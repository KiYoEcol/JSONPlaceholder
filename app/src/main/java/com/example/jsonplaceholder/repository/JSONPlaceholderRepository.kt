package com.example.jsonplaceholder.repository

import com.example.jsonplaceholder.model.AlbumModel
import com.example.jsonplaceholder.model.CommentModel
import com.example.jsonplaceholder.model.PhotoModel
import com.example.jsonplaceholder.network.JSONPlaceholderNetwork
import com.example.jsonplaceholder.model.PostModel
import com.example.jsonplaceholder.model.UserModel
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

    suspend fun getPostFlow(id: Int): Flow<Future<PostModel>> {
        return apiFlow { JSONPlaceholderNetwork.service.getPost(id) }
    }

    suspend fun getCommentsOnPostFlow(postId: Int): Flow<Future<List<CommentModel>>> {
        return apiFlow { JSONPlaceholderNetwork.service.getCommentsOnPost(postId) }
    }

    suspend fun getAlbumsFlow(): Flow<Future<List<AlbumModel>>> {
        return apiFlow { JSONPlaceholderNetwork.service.getAlbums() }
    }

    suspend fun getPhotosOnAlbum(albumId: Int): Flow<Future<List<PhotoModel>>> {
        return apiFlow { JSONPlaceholderNetwork.service.getPhotosOnAlbum(albumId) }
    }

    suspend fun getPhotoFlow(photoId: Int): Flow<Future<PhotoModel>> {
        return apiFlow { JSONPlaceholderNetwork.service.getPhoto(photoId) }
    }

    suspend fun getUserFlow(id: Int): Flow<Future<UserModel>> {
        return apiFlow { JSONPlaceholderNetwork.service.getUser(id) }
    }
}