package com.andreromano.foodmix.ui.mapper

import com.andreromano.foodmix.core.Resource
import com.andreromano.foodmix.ui.model.ListState

suspend fun <T> Resource<List<T>>.toListState(): ListState<T> =
    if (!this.data.isNullOrEmpty()) ListState.Results(this.data!!)
    else when (this) {
        is Resource.Loading -> ListState.Loading
        is Resource.Success -> ListState.EmptyState
        is Resource.Failure -> ListState.Error(this.error)
    }