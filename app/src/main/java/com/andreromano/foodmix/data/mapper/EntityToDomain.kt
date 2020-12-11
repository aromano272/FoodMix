package com.andreromano.foodmix.data.mapper

import com.andreromano.foodmix.database.model.CategoryEntity
import com.andreromano.foodmix.database.model.IngredientEntity
import com.andreromano.foodmix.database.model.IngredientTypeEntity
import com.andreromano.foodmix.domain.model.Category
import com.andreromano.foodmix.domain.model.Ingredient
import com.andreromano.foodmix.domain.model.IngredientType

suspend fun CategoryEntity.toDomain(): Category = Category(
    id = id,
    name = name,
    imageUrl = imageUrl
)

suspend fun IngredientTypeEntity.toDomain(): IngredientType = when (this) {
    IngredientTypeEntity.MEAT -> IngredientType.MEAT
    IngredientTypeEntity.FISH -> IngredientType.FISH
    IngredientTypeEntity.VEGETABLES -> IngredientType.VEGETABLES
    IngredientTypeEntity.FRUITS -> IngredientType.FRUITS
    IngredientTypeEntity.GRAINS -> IngredientType.GRAINS
}

suspend fun IngredientEntity.toDomain(): Ingredient = Ingredient(
    id = id,
    name = name,
    imageUrl = imageUrl,
    type = type.toDomain()
)