package com.andreromano.foodmix.domain.model

import android.os.Parcelable
import com.andreromano.foodmix.core.CategoryId
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    val id: CategoryId,
    val name: String,
    val imageUrl: String
) : Parcelable