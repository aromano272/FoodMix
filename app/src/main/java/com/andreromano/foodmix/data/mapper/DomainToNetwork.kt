package com.andreromano.foodmix.data.mapper

import com.andreromano.foodmix.domain.model.RecipesOrderBy
import com.andreromano.foodmix.network.model.CreateDirectionRequest
import com.andreromano.foodmix.network.model.CreateRecipeRequest
import com.andreromano.foodmix.network.model.RecipesOrderByRequest

suspend fun com.andreromano.foodmix.domain.model.CreateRecipeRequest.toNetwork(): CreateRecipeRequest = CreateRecipeRequest(
    title = this.title,
    description = this.description,
    image = this.image,
    categories = this.categories,
    cookingTime = this.cookingTime,
    servingsCount = this.servingsCount,
    calories = this.calories,
    ingredients = this.ingredients,
    directions = this.directions.map { it.toNetwork() },
)

suspend fun com.andreromano.foodmix.domain.model.CreateDirectionRequest.toNetwork(): CreateDirectionRequest = CreateDirectionRequest(
    title = this.title,
    description = this.description,
    image = this.image
)

suspend fun RecipesOrderBy.toNetwork(): RecipesOrderByRequest = RecipesOrderByRequest.DURATION