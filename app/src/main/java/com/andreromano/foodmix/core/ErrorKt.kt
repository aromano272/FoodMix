package com.andreromano.foodmix.core

sealed class ErrorKt {
    data class Unknown(val message: String) : ErrorKt()
    object Unauthorized : ErrorKt()
    object Generic : ErrorKt()
    object Network : ErrorKt() // todo: this represents unknown network errors, maybe this should have a better name
}