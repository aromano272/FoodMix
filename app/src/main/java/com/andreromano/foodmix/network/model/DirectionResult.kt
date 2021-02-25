package com.andreromano.foodmix.network.model

import com.andreromano.foodmix.core.DirectionId
import com.andreromano.foodmix.network.mapper.FixImageUrlForEmulator
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DirectionResult(
    val id: DirectionId,
    val title: String,
    val description: String,
    @FixImageUrlForEmulator
    val imageUrl: String?,
)