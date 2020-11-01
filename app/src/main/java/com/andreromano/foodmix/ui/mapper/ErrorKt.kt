package com.andreromano.foodmix.ui.mapper

import com.andreromano.foodmix.core.ErrorKt

val ErrorKt.errorMessage: String
    get() = when (this) {
        is ErrorKt.Unknown -> "Unknown"
        ErrorKt.Unauthorized -> "Unauthorized"
        ErrorKt.Generic -> "Generic"
        ErrorKt.Network -> "Network"
    }