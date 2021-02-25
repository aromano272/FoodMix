package com.andreromano.foodmix.domain.model

import android.os.Parcelable
import com.andreromano.foodmix.core.IngredientTypeId
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IngredientType(
    val id: IngredientTypeId,
    val name: String
) : Parcelable