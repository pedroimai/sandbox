package com.pedroimai.characters

import com.pedroimai.shared.domain.CharactersPayload
import com.pedroimai.shared.network.MarvelApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class CharacterRepository @Inject constructor() {
    private val marvelApi = MarvelApi.Impl() //todo inject marvelApi

    fun getCharacters(): Flow<Result<CharactersPayload.Characters>> = flow {
        emit(
            Result.success(marvelApi.getCharacters().data) as Result<CharactersPayload.Characters>
        )
    }.onStart {
        emit(Result.loading<CharactersPayload.Characters>() as Result<CharactersPayload.Characters>)
    }.catch { exception ->
        emit(Result.failed<CharactersPayload.Characters>(exception) as Result<CharactersPayload.Characters>)
    }.flowOn(Dispatchers.IO)
}
