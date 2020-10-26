package com.andreromano.foodmix.domain.model

import android.os.Parcelable
import com.andreromano.foodmix.core.UserId
import kotlinx.android.parcel.Parcelize


@Parcelize
data class User(
    val id: UserId,
    val username: String,
    val avatarUrl: String
) : Parcelable