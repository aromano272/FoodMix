package com.andreromano.foodmix.data.mapper

import com.andreromano.foodmix.domain.model.Category
import com.andreromano.foodmix.domain.model.Ingredient
import com.andreromano.foodmix.domain.model.IngredientType
import com.andreromano.foodmix.network.model.CategoryResult
import com.andreromano.foodmix.network.model.IngredientResult
import com.andreromano.foodmix.network.model.IngredientTypeResult


suspend fun CategoryResult.toDomain(): Category = Category(
    id = id,
    name = name,
    imageUrl = imageUrl
)

suspend fun IngredientTypeResult.toDomain(): IngredientType = when (this) {
    IngredientTypeResult.MEAT -> IngredientType.MEAT
    IngredientTypeResult.FISH -> IngredientType.FISH
    IngredientTypeResult.VEGETABLES -> IngredientType.VEGETABLES
    IngredientTypeResult.FRUITS -> IngredientType.FRUITS
    IngredientTypeResult.GRAINS -> IngredientType.GRAINS
}

suspend fun IngredientResult.toDomain(): Ingredient = Ingredient(
    id = id,
    name = name,
    imageUrl = imageUrl,
    type =  type.toDomain()
)