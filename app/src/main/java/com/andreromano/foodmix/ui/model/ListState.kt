package com.andreromano.foodmix.ui.model

import com.andreromano.foodmix.core.ErrorKt

sealed class ListState<out T> {
    object Loading : ListState<Nothing>()
    data class Results<T>(val results: List<T>) : ListState<T>()
    object EmptyState : ListState<Nothing>()
    data class Error(val error: ErrorKt) : ListState<Nothing>()
}