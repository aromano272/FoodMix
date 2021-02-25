package com.andreromano.foodmix.data.mapper

import com.andreromano.foodmix.domain.model.*
import com.andreromano.foodmix.network.model.*


suspend fun CategoryResult.toDomain(): Category = Category(
    id = id,
    name = name,
    imageUrl = imageUrl
)

suspend fun IngredientResult.toDomain(): Ingredient = Ingredient(
    id = id,
    name = name,
    imageUrl = imageUrl,
    type =  IngredientType(type, type)
)

suspend fun DirectionResult.toDomain(): Direction = Direction(
    id = id,
    title = title,
    description = description,
    imageUrl = imageUrl
)

suspend fun UserResult.toDomain(): User = User(
    id = id,
    username = username,
    avatarUrl = avatarUrl
)

suspend fun ReviewResult.toDomain(): Review = Review(
    id = id,
    user = user.toDomain(),
    comment = comment,
    timestamp = timestamp,
    likes = likes,
    isFavorite = isFavorite
)

suspend fun RecipeResult.toDomain(): Recipe = Recipe(
    id = id,
    title = title,
    description = description,
    isFavorite = isFavorite,
    imageUrl = imageUrl,
    rating = rating,
    ratingsCount = ratingsCount,
    calories = calories,
    servings = servings,
    cookingTime = cookingTime,
    categories = categories.map { it.toDomain() },
    ingredients = ingredients.map { it.toDomain() },
    directions = directions.map { it.toDomain() },
    reviews = reviews.map { it.toDomain() },
)