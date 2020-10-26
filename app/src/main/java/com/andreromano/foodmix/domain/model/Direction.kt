package com.andreromano.foodmix.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Direction(
    val description: String,
    val imageUrl: String
) : Parcelable