package com.andreromano.foodmix.network.model

import com.andreromano.foodmix.core.IngredientId
import com.andreromano.foodmix.domain.model.IngredientType
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IngredientResult(
    val id: IngredientId,
    val name: String,
    val imageUrl: String,
    val type: IngredientTypeResult
)