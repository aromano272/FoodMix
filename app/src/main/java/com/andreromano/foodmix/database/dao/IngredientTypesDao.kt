package com.andreromano.foodmix.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.TypeConverters
import com.andreromano.foodmix.core.IngredientTypeId
import com.andreromano.foodmix.database.mapper.RoomTypeConverters
import com.andreromano.foodmix.database.model.IngredientTypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class IngredientTypesDao : BaseDao<IngredientTypeEntity>() {

    @Query("SELECT * FROM ingredienttypeentity")
    abstract fun getAll(): Flow<List<IngredientTypeEntity>>

    @Query("DELETE FROM ingredienttypeentity WHERE ingredient_type_id NOT IN (:ids)")
    protected abstract suspend fun deleteAllOther(ids: List<IngredientTypeId>)

    @Transaction
    open suspend fun replaceAll(types: List<IngredientTypeEntity>) {
        deleteAllOther(types.map { it.id })
        upsert(types)
    }
}