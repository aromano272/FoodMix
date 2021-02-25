package com.andreromano.foodmix.database.model

import androidx.room.*
import com.andreromano.foodmix.core.IngredientId
import com.andreromano.foodmix.core.IngredientTypeId


data class IngredientQuery(
    @ColumnInfo(name = "ingredient_id")
    val id: IngredientId,
    @ColumnInfo(name = "ingredient_name")
    val name: String,
    @ColumnInfo(name = "ingredient_imageUrl")
    val imageUrl: String?,

    @ColumnInfo(name = "ingredient_typeId")
    val typeId: IngredientTypeId,

    @Relation(entity = IngredientTypeEntity::class, entityColumn = "ingredient_type_id", parentColumn = "ingredient_typeId")
    val type: IngredientTypeEntity
)
