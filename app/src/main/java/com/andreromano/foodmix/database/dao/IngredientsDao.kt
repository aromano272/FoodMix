package com.andreromano.foodmix.database.dao

import androidx.room.*
import com.andreromano.foodmix.core.IngredientId
import com.andreromano.foodmix.core.IngredientTypeId
import com.andreromano.foodmix.database.mapper.RoomTypeConverters
import com.andreromano.foodmix.database.model.IngredientEntity
import com.andreromano.foodmix.database.model.IngredientQuery
import com.andreromano.foodmix.database.model.IngredientTypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class IngredientsDao : BaseDao<IngredientEntity>() {

    @Query(
        """
            SELECT * 
            FROM ingrediententity 
            WHERE ingredient_name LIKE :searchQuery || '%'
            AND CASE
                WHEN :ingredientTypeId IS NOT NULL THEN ingredient_typeId = :ingredientTypeId
                ELSE 1
            END
        """
    )
    abstract fun getAll(searchQuery: String = "", ingredientTypeId: IngredientTypeId?): Flow<List<IngredientQuery>>

    @Query("DELETE FROM ingrediententity WHERE ingredient_id NOT IN (:ids)")
    abstract suspend fun deleteAllOther(ids: List<IngredientId>)

    @Transaction
    open suspend fun replaceAll(ingredients: List<IngredientEntity>) {
        deleteAllOther(ingredients.map { it.id })
        upsert(ingredients)
    }
}