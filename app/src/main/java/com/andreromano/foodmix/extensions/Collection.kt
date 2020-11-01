package com.andreromano.foodmix.extensions


fun <T> List<T>?.orNull() = if (this.isNullOrEmpty()) null else this

fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    val tmp = this[index1] // ‘this’ corresponds to the list
    this[index1] = this[index2]
    this[index2] = tmp
}

fun <T> List<T>.swap(index1: Int, index2: Int): List<T> = toMutableList().apply { swap(index1,index2) }.toList()
