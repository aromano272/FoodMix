package com.andreromano.foodmix.database.model

import androidx.room.*
import com.andreromano.foodmix.core.IngredientId
import com.andreromano.foodmix.core.IngredientTypeId
import com.andreromano.foodmix.database.mapper.RoomTypeConverters


@Entity(
    foreignKeys = [
        ForeignKey(
            entity = IngredientTypeEntity::class,
            parentColumns = ["ingredient_type_id"],
            childColumns = ["ingredient_typeId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class IngredientEntity(
    @PrimaryKey
    @ColumnInfo(name = "ingredient_id")
    val id: IngredientId,
    @ColumnInfo(name = "ingredient_name")
    val name: String,
    @ColumnInfo(name = "ingredient_imageUrl")
    val imageUrl: String?,
    @ColumnInfo(name = "ingredient_typeId")
    val typeId: IngredientTypeId
)
