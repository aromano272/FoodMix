package com.andreromano.foodmix.ui.mapper

import com.andreromano.foodmix.core.Resource
import com.andreromano.foodmix.ui.model.ListState

suspend fun <T> Resource<List<T>>.toListState(): ListState<T> =
    when (this) {
        is Resource.Loading ->
            if (this.data.isNullOrEmpty()) ListState.Loading(null)
            else ListState.Loading(ListState.Results(this.data!!))
        is Resource.Success ->
            if (this.data.isEmpty()) ListState.EmptyState
            else ListState.Results(this.data)
        is Resource.Failure -> ListState.Error(this.error)
    }