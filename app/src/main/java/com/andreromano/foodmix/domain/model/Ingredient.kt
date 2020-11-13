package com.andreromano.foodmix.domain.model

import android.os.Parcelable
import com.andreromano.foodmix.core.IngredientId
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ingredient(
    val id: IngredientId,
    val name: String,
    val imageUrl: String
) : Parcelable