package com.andreromano.foodmix.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andreromano.foodmix.core.IngredientTypeId

@Entity
data class IngredientTypeEntity(
    @PrimaryKey
    @ColumnInfo(name = "ingredient_type_id")
    val id: IngredientTypeId,

    @ColumnInfo(name = "ingredient_type_name")
    val name: String,
)