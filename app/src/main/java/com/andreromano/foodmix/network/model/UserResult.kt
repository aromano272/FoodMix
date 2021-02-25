package com.andreromano.foodmix.network.model

import com.andreromano.foodmix.core.UserId
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class UserResult(
    val id: UserId,
    val username: String,
    val avatarUrl: String,
)