package com.andreromano.foodmix.network.model

import com.andreromano.foodmix.core.CategoryId
import com.andreromano.foodmix.network.mapper.FixImageUrlForEmulator
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryResult(
    val id: CategoryId,
    val name: String,
    @FixImageUrlForEmulator
    val imageUrl: String
)