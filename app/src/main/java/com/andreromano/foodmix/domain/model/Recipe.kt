package com.andreromano.foodmix.domain.model

import android.os.Parcelable
import androidx.annotation.FloatRange
import com.andreromano.foodmix.core.Ingredient
import com.andreromano.foodmix.core.Minutes
import com.andreromano.foodmix.core.RecipeId
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(
    val id: RecipeId,
    val title: String,
    val description: String,
    val isFavorite: Boolean,
    val imageUrl: String,
    @FloatRange(from = 0.0, to = 5.0)
    val rating: Double,
    val ratingsCount: Int,
    val calories: Int,
    val servings: Int,
    val duration: Minutes,
    val categories: List<Category>,
    val ingredients: List<Ingredient>,
    val directions: List<Direction>,
    val reviews: List<Review>
) : Parcelable