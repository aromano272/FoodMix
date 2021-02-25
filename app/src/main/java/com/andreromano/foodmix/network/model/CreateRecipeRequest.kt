package com.andreromano.foodmix.network.model

import android.net.Uri
import com.andreromano.foodmix.core.CategoryId
import com.andreromano.foodmix.core.IngredientId
import com.andreromano.foodmix.core.Minutes
import com.andreromano.foodmix.domain.model.Direction
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class CreateRecipeRequest(
    val title: String,
    val description: String,
    val image: Uri?,
    val categories: List<CategoryId>,
    val cookingTime: Minutes,
    val servingsCount: Int,
    val calories: Int,
    val ingredients: List<IngredientId>,
    val directions: List<CreateDirectionRequest>
)