package com.andreromano.foodmix.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andreromano.foodmix.database.dao.CategoriesDao
import com.andreromano.foodmix.database.model.CategoryEntity


@Database(
    entities = [
        CategoryEntity::class
    ], version = 102
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoriesDao(): CategoriesDao
}