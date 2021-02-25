package com.andreromano.foodmix.network.model

import com.andreromano.foodmix.core.Millis
import com.andreromano.foodmix.core.ReviewId
import com.andreromano.foodmix.domain.model.User
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewResult(
    val id: ReviewId,
    val user: UserResult,
    val comment: String,
    val timestamp: Millis,
    val likes: Int,
    val isFavorite: Boolean,
)