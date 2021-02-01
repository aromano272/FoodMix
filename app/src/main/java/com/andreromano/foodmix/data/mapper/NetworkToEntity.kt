package com.andreromano.foodmix.data.mapper

import com.andreromano.foodmix.database.model.CategoryEntity
import com.andreromano.foodmix.database.model.IngredientEntity
import com.andreromano.foodmix.database.model.IngredientTypeEntity
import com.andreromano.foodmix.database.model.UserProfileEntity
import com.andreromano.foodmix.network.model.CategoryResult
import com.andreromano.foodmix.network.model.IngredientResult
import com.andreromano.foodmix.network.model.IngredientTypeResult
import com.andreromano.foodmix.network.model.UserProfileResult


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

suspend fun UserProfileResult.toEntity(): UserProfileEntity = UserProfileEntity(
    id = id,
    username = username,
    description = description,
    avatarUrl = avatarUrl,
    backgroundUrl = backgroundUrl,
    totalRecipesCount = totalRecipesCount,
    totalCookbooks = totalCookbooks,
    myRecipesCount = myRecipesCount,
    myCookbooksCount = myCookbooksCount,
    shoppingListCount = shoppingListCount,
)