package com.andreromano.foodmix.data.mapper

import com.andreromano.foodmix.database.model.IngredientTypeEntity
import com.andreromano.foodmix.domain.model.IngredientType

suspend fun IngredientType.toEntity(): IngredientTypeEntity = when (this) {
    IngredientType.MEAT -> IngredientTypeEntity.MEAT
    IngredientType.FISH -> IngredientTypeEntity.FISH
    IngredientType.VEGETABLES -> IngredientTypeEntity.VEGETABLES
    IngredientType.FRUITS -> IngredientTypeEntity.FRUITS
    IngredientType.GRAINS -> IngredientTypeEntity.GRAINS
}