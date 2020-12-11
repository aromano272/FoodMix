package com.andreromano.foodmix.data.mapper

import com.andreromano.foodmix.database.model.CategoryEntity
import com.andreromano.foodmix.database.model.IngredientEntity
import com.andreromano.foodmix.database.model.IngredientTypeEntity
import com.andreromano.foodmix.network.model.CategoryResult
import com.andreromano.foodmix.network.model.IngredientResult
import com.andreromano.foodmix.network.model.IngredientTypeResult


suspend fun CategoryResult.toEntity(): CategoryEntity = CategoryEntity(
    id = id,
    name = name,
    imageUrl = imageUrl
)

suspend fun IngredientTypeResult.toEntity(): IngredientTypeEntity = when (this) {
    IngredientTypeResult.MEAT -> IngredientTypeEntity.MEAT
    IngredientTypeResult.FISH -> IngredientTypeEntity.FISH
    IngredientTypeResult.VEGETABLES -> IngredientTypeEntity.VEGETABLES
    IngredientTypeResult.FRUITS -> IngredientTypeEntity.FRUITS
    IngredientTypeResult.GRAINS -> IngredientTypeEntity.GRAINS
}

suspend fun IngredientResult.toEntity(): IngredientEntity = IngredientEntity(
    id = id,
    name = name,
    imageUrl = imageUrl,
    type = type.toEntity()
)