package com.andreromano.foodmix.network.model

import com.andreromano.foodmix.core.Minutes
import com.andreromano.foodmix.core.RecipeId
import com.andreromano.foodmix.network.mapper.FixImageUrlForEmulator
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class RecipeResult(
    val id: RecipeId,
    val title: String,
    val description: String,
    val isFavorite: Boolean,
    @FixImageUrlForEmulator
    val imageUrl: String?,
    val rating: Double,
    val ratingsCount: Int,
    val calories: Int,
    val servings: Int,
    val cookingTime: Minutes,
    val categories: List<CategoryResult>,
    val ingredients: List<IngredientResult>,
    val directions: List<DirectionResult>,
    val reviews: List<ReviewResult>
)