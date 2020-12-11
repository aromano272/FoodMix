package com.andreromano.foodmix.database.mapper

import androidx.room.TypeConverter
import com.andreromano.foodmix.database.model.IngredientTypeEntity

object RoomTypeConverters {

    @TypeConverter
    @JvmStatic
    fun fromIngredientTypeEntityToTag(type: IngredientTypeEntity?): String? = type?.tag

    @TypeConverter
    @JvmStatic
    fun fromTagToIngredientTypeEntity(tag: String?): IngredientTypeEntity? = IngredientTypeEntity.values().first { it.tag == tag }

}