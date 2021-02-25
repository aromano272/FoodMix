package com.andreromano.foodmix.domain.model

import android.os.Parcelable
import com.andreromano.foodmix.core.IngredientId
import kotlinx.android.parcel.Parcelize

@Parcelize
// TODO: Differentiate between "verified ingredients" with ids, image and type; and "user generated ingredients" the ones that the user only sets the name
data class Ingredient(
    val id: IngredientId,
    val name: String,
    val imageUrl: String?,
    val type: IngredientType
) : Parcelable