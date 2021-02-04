package com.andreromano.foodmix.domain.model

import android.os.Parcelable
import com.andreromano.foodmix.core.UserId
import kotlinx.android.parcel.Parcelize


@Parcelize
data class UserProfile(
    val id: UserId,
    val username: String,
    val description: String,
    val avatarUrl: String,
    val backgroundUrl: String,

    val totalRecipesCount: Int,
    val totalCookbooksCount: Int,
    val myRecipesCount: Int,
    val myCookbooksCount: Int,
    val shoppingListCount: Int,
) : Parcelable