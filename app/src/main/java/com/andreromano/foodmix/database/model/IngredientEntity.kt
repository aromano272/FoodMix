package com.andreromano.foodmix.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.andreromano.foodmix.core.IngredientId
import com.andreromano.foodmix.database.mapper.RoomTypeConverters


@Entity
data class IngredientEntity(
    @PrimaryKey
    @ColumnInfo(name = "ingredient_id")
    val id: IngredientId,
    @ColumnInfo(name = "ingredient_name")
    val name: String,
    @ColumnInfo(name = "ingredient_imageUrl")
    val imageUrl: String,
    @field:TypeConverters(RoomTypeConverters::class)
    @ColumnInfo(name = "ingredient_type")
    val type: IngredientTypeEntity
)
