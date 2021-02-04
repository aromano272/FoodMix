package com.andreromano.foodmix.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andreromano.foodmix.core.UserId


@Entity
data class UserProfileEntity(
    @PrimaryKey
    @ColumnInfo(name = "userprofile_id") val id: UserId,
    @ColumnInfo(name = "userprofile_username") val username: String,
    @ColumnInfo(name = "userprofile_description") val description: String,
    @ColumnInfo(name = "userprofile_avatarUrl") val avatarUrl: String,
    @ColumnInfo(name = "userprofile_backgroundUrl") val backgroundUrl: String,

    @ColumnInfo(name = "userprofile_totalRecipesCount") val totalRecipesCount: Int,
    @ColumnInfo(name = "userprofile_totalCookbooksCount") val totalCookbooksCount: Int,
    @ColumnInfo(name = "userprofile_myRecipesCount") val myRecipesCount: Int,
    @ColumnInfo(name = "userprofile_myCookbooksCount") val myCookbooksCount: Int,
    @ColumnInfo(name = "userprofile_shoppingListCount") val shoppingListCount: Int,
)