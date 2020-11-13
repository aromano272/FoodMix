package com.andreromano.foodmix.extensions

import com.andreromano.foodmix.core.CombineResult
import com.andreromano.foodmix.core.ErrorKt
import kotlinx.coroutines.flow.*


fun <T> Flow<T>.catchError(action: suspend FlowCollector<T>.(cause: ErrorKt) -> Unit) = catch { cause ->
    if (cause is ErrorKt) action(cause)
    else throw cause
}

fun <T> Flow<T>.startWithPreEmission(): Flow<CombineResult<T>> =
    mapLatest { CombineResult.Emission(it) as CombineResult<T> }.onStart { emit(CombineResult.ToBeEmitted) }











