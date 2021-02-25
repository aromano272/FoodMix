package com.andreromano.foodmix.domain.model

import android.net.Uri
import com.andreromano.foodmix.core.CategoryId
import com.andreromano.foodmix.core.IngredientId
import com.andreromano.foodmix.core.Minutes

data class CreateRecipeRequest(
    val title: String,
    val description: String,
    val image: Uri?,
    val categories: List<CategoryId>,
    val cookingTime: Minutes,
    val calories: Int,
    val servingsCount: Int,
    val ingredients: List<IngredientId>,
    val directions: List<CreateDirectionRequest>,
)