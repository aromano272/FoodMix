package com.andreromano.foodmix.network.model

import com.andreromano.foodmix.core.CategoryId
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryResult(
    val id: CategoryId,
    val name: String,
    val imageUrl: String
)