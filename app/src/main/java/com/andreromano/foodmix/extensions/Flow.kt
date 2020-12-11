package com.andreromano.foodmix.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreromano.foodmix.core.CombineResult
import com.andreromano.foodmix.core.Resource
import com.andreromano.foodmix.core.ResultKt
import com.andreromano.foodmix.ui.mapper.toListState
import com.andreromano.foodmix.ui.model.ListState
import kotlinx.coroutines.flow.*


fun <T> Flow<T>.startWithPreEmission(): Flow<CombineResult<T>> =
    mapLatest { CombineResult.Emission(it) as CombineResult<T> }
        .onStart { emit(CombineResult.ToBeEmitted) }

fun <T> Flow<Resource<T>>.filterResourceSuccess(): Flow<Resource.Success<T>> =
    mapNotNull { if (it is Resource.Success) it else null }

fun <T> Flow<Resource<T>>.filterResourceFailure(): Flow<Resource.Failure<T>> =
    mapNotNull { if (it is Resource.Failure) it else null }

fun <T> Flow<Resource<T>>.filterResourceLoading(): Flow<Resource.Loading<T>> =
    mapNotNull { if (it is Resource.Loading) it else null }

fun <T> Flow<Resource<T>>.toResourceCompletable(): Flow<Resource<Unit>> =
    mapResourceData { Unit }

fun <X, Y> Flow<Resource<X>>.mapResourceData(body: suspend (X) -> Y): Flow<Resource<Y>> =
    map { it.mapData(body) }

fun <T> Flow<ResultKt<T>>.filterResultSuccess(): Flow<ResultKt.Success<T>> =
    mapNotNull { if (it is ResultKt.Success) it else null }

fun <T> Flow<ResultKt<T>>.filterResultFailure(): Flow<ResultKt.Failure> =
    mapNotNull { if (it is ResultKt.Failure) it else null }

fun <T> Flow<ResultKt<T>>.toResultCompletable(): Flow<ResultKt<Unit>> =
    mapResultData { Unit }

fun <X, Y> Flow<ResultKt<X>>.mapResultData(body: suspend (X) -> Y): Flow<ResultKt<Y>> =
    map { it.mapData(body) }

fun <T> Flow<T>.shareHere(viewModel: ViewModel): SharedFlow<T> = this.shareIn(viewModel.viewModelScope, SharingStarted.Lazily, 1)

fun <T> Flow<Resource<List<T>>>.asListState(keepPreviousResultsWhileLoading: Boolean = true) =
    this.mapLatest { it.toListState() }
        .run {
            if (keepPreviousResultsWhileLoading) {
                runningReduce { old, new ->
                    if (new is ListState.Loading && new.results == null) {
                        when (old) {
                            is ListState.Loading -> new.copy(old.results)
                            is ListState.Results -> new.copy(old)
                            else -> new
                        }
                    } else {
                        new
                    }
                }
            } else this
        }
