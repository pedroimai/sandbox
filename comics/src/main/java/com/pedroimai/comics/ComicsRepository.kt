package com.pedroimai.comics

import com.pedroimai.shared.domain.ComicsPayload
import com.pedroimai.shared.network.MarvelApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ComicsRepository @Inject constructor() {
    private val marvelApi = MarvelApi.Impl() //todo inject marvelApi

    fun getComics(): Flow<Result<ComicsPayload.Comics>> = flow {
        emit(
            Result.success(marvelApi.getComics().data) as Result<ComicsPayload.Comics>
        )
    }.onStart {
        emit(Result.loading<ComicsPayload.Comics>() as Result<ComicsPayload.Comics>)
    }.catch { exception ->
        emit(Result.failed<ComicsPayload.Comics>(exception) as Result<ComicsPayload.Comics>)
    }.flowOn(Dispatchers.IO)
}
