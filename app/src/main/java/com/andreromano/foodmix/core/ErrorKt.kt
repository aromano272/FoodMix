package com.andreromano.foodmix.core

sealed class ErrorKt : Throwable() {
    data class Unknown(override val message: String) : ErrorKt()
    object Unauthorized : ErrorKt()
    object Generic : ErrorKt()
    object NotFound : ErrorKt()
    object Network : ErrorKt() // todo: this represents unknown network errors, maybe this should have a better name
}