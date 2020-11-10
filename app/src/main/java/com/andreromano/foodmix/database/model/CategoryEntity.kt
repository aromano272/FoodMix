package com.andreromano.foodmix.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andreromano.foodmix.core.CategoryId


@Entity
data class CategoryEntity(
    @PrimaryKey
    @ColumnInfo(name = "category_id")
    val id: CategoryId,
    @ColumnInfo(name = "category_name")
    val name: String,
    @ColumnInfo(name = "category_imageUrl")
    val imageUrl: String
)