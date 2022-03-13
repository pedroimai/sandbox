package com.pedroimai.comics

sealed class Result<T> {
    class Loading<T> : Result<T>()
    data class Success<T>(val data: T) : Result<T>()
    data class Failed<T>(val error: Throwable) : Result<T>()

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(data: T) = Success(data)
        fun <T> failed(error: Throwable) = Failed<T>(error)
    }
}