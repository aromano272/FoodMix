package com.andreromano.foodmix.domain.model

import android.os.Parcelable
import com.andreromano.foodmix.core.DirectionId
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Direction(
    val id: DirectionId,
    val title: String,
    val description: String,
    val imageUrl: String?
) : Parcelable