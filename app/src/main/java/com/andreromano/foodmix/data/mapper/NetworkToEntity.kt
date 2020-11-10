package com.andreromano.foodmix.data.mapper

import com.andreromano.foodmix.database.model.CategoryEntity
import com.andreromano.foodmix.network.model.CategoryResult


suspend fun List<CategoryResult>.toEntity(): List<CategoryEntity> = map { it.toEntity() }

suspend fun CategoryResult.toEntity(): CategoryEntity = CategoryEntity(
    id = id,
    name = name,
    imageUrl = imageUrl
)