package com.andreromano.foodmix.core

sealed class CombineResult<out T> {
    object ToBeEmitted : CombineResult<Nothing>()
    data class Emission<out T>(val data: T) : CombineResult<T>()

    fun orNull(): T? = when (this) {
        is ToBeEmitted -> null
        is Emission -> this.data
    }
}