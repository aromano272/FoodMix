package com.andreromano.foodmix.extensions

import com.andreromano.foodmix.core.ErrorKt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch


fun <T> Flow<T>.catchError(action: suspend FlowCollector<T>.(cause: ErrorKt) -> Unit) = catch { cause ->
    if (cause is ErrorKt) action(cause)
    else throw cause
}











