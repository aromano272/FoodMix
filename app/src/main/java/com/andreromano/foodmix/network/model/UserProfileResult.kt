package com.andreromano.foodmix.network.model

import com.andreromano.foodmix.core.UserId
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserProfileResult(
    val id: UserId,
    val username: String,
    val description: String,
    val avatarUrl: String,
    val backgroundUrl: String,

    val totalRecipesCount: Int,
    val totalCookbooks: Int,
    val myRecipesCount: Int,
    val myCookbooksCount: Int,
    val shoppingListCount: Int,
)