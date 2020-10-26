package com.andreromano.foodmix.ui.model

sealed class ListState<out T> {
    object Loading : ListState<Nothing>()
    data class Results<T>(val results: List<T>) : ListState<T>()
    object EmptyState : ListState<Nothing>()
    data class Error<T>(val error: Exception) : ListState<T>()
}