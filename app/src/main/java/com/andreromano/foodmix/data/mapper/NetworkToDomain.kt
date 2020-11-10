package com.andreromano.foodmix.data.mapper

import com.andreromano.foodmix.domain.model.Category
import com.andreromano.foodmix.network.model.CategoryResult


suspend fun List<CategoryResult>.toDomain(): List<Category> = map { it.toDomain() }

suspend fun CategoryResult.toDomain(): Category = Category(
    id = id,
    name = name,
    imageUrl = imageUrl
)