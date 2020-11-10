package com.andreromano.foodmix.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.andreromano.foodmix.core.CategoryId
import com.andreromano.foodmix.database.model.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CategoriesDao : BaseDao<CategoryEntity>() {

    @Query("SELECT * FROM categoryentity WHERE category_name LIKE :searchQuery || '%'")
    abstract fun getAll(searchQuery: String = ""): Flow<List<CategoryEntity>>

    @Query("DELETE FROM categoryentity WHERE category_id NOT IN (:ids)")
    abstract suspend fun deleteAllOther(ids: List<CategoryId>)

    @Transaction
    open suspend fun replaceAll(categories: List<CategoryEntity>) {
        deleteAllOther(categories.map { it.id })
        upsert(categories)
    }

}