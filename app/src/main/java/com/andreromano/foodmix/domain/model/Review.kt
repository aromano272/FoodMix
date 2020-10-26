package com.andreromano.foodmix.domain.model

import android.os.Parcelable
import com.andreromano.foodmix.core.Millis
import com.andreromano.foodmix.core.ReviewId
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Review(
    val id: ReviewId,
    val user: User,
    val comment: String,
    val timestamp: Millis,
    val likes: Int,
    val isFavorite: Boolean
) : Parcelable