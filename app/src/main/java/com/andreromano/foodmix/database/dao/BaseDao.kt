package com.andreromano.foodmix.database.dao

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery


abstract class BaseDao<in T : Any> {
    /**
     * Insert an object in the database.
     *
     * @param obj the object to be inserted.
     * @return The SQLite row id
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertOrIgnore(obj: T): Long

    /**
     * Insert an array of objects in the database.
     *
     * @param obj the objects to be inserted.
     * @return The SQLite row ids
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertOrIgnore(obj: List<T>): List<Long>

    /**
     * Update an object from the database.
     *
     * @param obj the object to be updated
     */
    @Update
    abstract suspend fun update(obj: T)

    /**
     * Update an array of objects from the database.
     *
     * @param obj the object to be updated
     */
    @Update
    abstract suspend fun update(obj: List<T>)

    /**
     * Delete an object from the database
     *
     * @param obj the object to be deleted
     */
    @Delete
    abstract suspend fun delete(obj: List<T>)

    /**
     * Delete an object from the database
     *
     * @param obj the object to be deleted
     */
    @Delete
    abstract suspend fun delete(obj: T)

    @Transaction
    open suspend fun upsert(obj: T) {
        val id = insertOrIgnore(obj)
        if (id == -1L) {
            update(obj)
        }
    }

    @Transaction
    open suspend fun upsert(objList: List<T>) {
        val insertResult = insertOrIgnore(objList)
        val updateList = insertResult.mapIndexedNotNull { index, value ->
            if (value == -1L) objList[index] else null
        }

        if (updateList.isNotEmpty()) {
            update(updateList)
        }
    }

    @RawQuery
    protected abstract suspend fun deleteAllRawQuery(query: SupportSQLiteQuery): Int

    @Transaction
    protected open suspend fun replaceAllRawQuery(table: String, deleteByColumn: String, deleteIds: List<Any>, objList: List<T>) {
        val sql = "DELETE FROM $table WHERE $deleteByColumn NOT IN (${deleteIds.joinToString(", ") { "'$it'" }})"
        val query = SimpleSQLiteQuery(sql)
        deleteAllRawQuery(query)
        upsert(objList)
    }

}