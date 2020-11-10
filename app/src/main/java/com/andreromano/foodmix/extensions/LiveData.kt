package com.andreromano.foodmix.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations


fun <X, Y> LiveData<X>.map(body: (X) -> Y): LiveData<Y> {
    return Transformations.map(this, body)
}

/**
 * Maps any values that were emitted by the LiveData to the given function that produces another LiveData
 */
fun <X, Y> LiveData<X>.switchMap(body: (X) -> LiveData<Y>): LiveData<Y> {
    return Transformations.switchMap(this, body)
}

fun <T> LiveData<T>.distinctUntilChanged(): LiveData<T> {
    return Transformations.distinctUntilChanged(this)
}
