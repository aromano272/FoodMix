package com.andreromano.foodmix.data.mapper

import com.andreromano.foodmix.database.model.CategoryEntity
import com.andreromano.foodmix.domain.model.Category

suspend fun List<CategoryEntity>.toDomain(): List<Category> = map { it.toDomain() }

suspend fun CategoryEntity.toDomain(): Category = Category(
    id = id,
    name = name,
    imageUrl = imageUrl
)