package com.pedroimai.comics

import com.pedroimai.shared.network.MarvelApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class ComicsModel(val id: String, val title: String)

class ComicsRepository @Inject constructor(private val mapper: ComicsModelMapper) {
    private val marvelApi = MarvelApi.Impl() //todo inject marvelApi

    fun getComics(page: Int): Flow<Result<List<ComicsModel>>> = flow {
        emit(
            Result.success(mapper(marvelApi.getComics(page))) as Result<List<ComicsModel>>
        )
    }.onStart {
        emit(Result.loading())
    }.catch { exception ->
        emit(Result.failed(exception))
    }.flowOn(Dispatchers.IO)
}
