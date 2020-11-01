package com.andreromano.foodmix.core


sealed class ResultKt<out T> {
    data class Success<T>(val data: T) : ResultKt<T>()
    data class Failure(val error: ErrorKt) : ResultKt<Nothing>()

    suspend fun <R> mapData(body: suspend (T) -> R): ResultKt<R> = when (this) {
        is Success -> Success(body(data))
        is Failure -> this
    }
}