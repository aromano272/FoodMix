package com.andreromano.foodmix.network.model

import android.net.Uri


data class CreateDirectionRequest(
    val title: String,
    val description: String,
    val image: Uri?
)