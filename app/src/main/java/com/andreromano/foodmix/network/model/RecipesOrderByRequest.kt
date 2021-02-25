package com.andreromano.foodmix.network.model

import com.squareup.moshi.Json

enum class RecipesOrderByRequest {
    @Json(name = "RELEVANCE")
    RELEVANCE,
    @Json(name = "RATING")
    RATING,
    @Json(name = "DURATION")
    DURATION
}