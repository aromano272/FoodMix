package com.andreromano.foodmix.core

sealed class ResultFm<out T> {
    data class Success<T>(val data: T) : ResultFm<T>()
    data class Failure(val error: Exception) : ResultFm<Nothing>()

    fun <R> map(body: (T) -> R): ResultFm<R> = when (this) {
        is Success -> Success(body(data))
        is Failure -> this
    }
}