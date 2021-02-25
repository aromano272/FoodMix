package com.andreromano.foodmix.network.model

import com.andreromano.foodmix.core.IngredientId
import com.andreromano.foodmix.network.mapper.FixImageUrlForEmulator
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IngredientResult(
    val id: IngredientId,
    val name: String,
    @FixImageUrlForEmulator
    val imageUrl: String?,
    val type: String
)