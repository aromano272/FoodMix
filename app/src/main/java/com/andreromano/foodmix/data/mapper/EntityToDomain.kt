package com.andreromano.foodmix.data.mapper

import com.andreromano.foodmix.database.model.*
import com.andreromano.foodmix.domain.model.Category
import com.andreromano.foodmix.domain.model.Ingredient
import com.andreromano.foodmix.domain.model.IngredientType
import com.andreromano.foodmix.domain.model.UserProfile

suspend fun CategoryEntity.toDomain(): Category = Category(
    id = id,
    name = name,
    imageUrl = imageUrl
)

suspend fun IngredientTypeEntity.toDomain(): IngredientType = IngredientType(
    id = id,
    name = name
)

suspend fun IngredientQuery.toDomain(): Ingredient = Ingredient(
    id = id,
    name = name,
    imageUrl = imageUrl,
    type = type.toDomain()
)

suspend fun UserProfileEntity.toDomain(): UserProfile = UserProfile(
    id = id,
    username = username,
    description = description,
    avatarUrl = avatarUrl,
    backgroundUrl = backgroundUrl,
    totalRecipesCount = totalRecipesCount,
    totalCookbooksCount = totalCookbooksCount,
    myRecipesCount = myRecipesCount,
    myCookbooksCount = myCookbooksCount,
    shoppingListCount = shoppingListCount,
)