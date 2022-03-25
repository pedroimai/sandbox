package com.pedroimai.comics

import com.pedroimai.shared.domain.ComicsPayload
import com.pedroimai.shared.network.MarvelApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ComicsRepository @Inject constructor() {
    private val marvelApi = MarvelApi.Impl() //todo inject marvelApi

    fun getComics(page: Int): Flow<Result<List<ComicsPayload.Comics.ComicModel>>> = flow {
        emit(
            Result.success(marvelApi.getComics(page).data.comics) as Result<List<ComicsPayload.Comics.ComicModel>>
        )
    }.onStart {
        emit(Result.loading())
    }.catch { exception ->
        emit(Result.failed(exception))
    }.flowOn(Dispatchers.IO)
}
