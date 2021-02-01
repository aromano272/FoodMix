package com.andreromano.foodmix.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andreromano.foodmix.database.dao.CategoriesDao
import com.andreromano.foodmix.database.dao.IngredientsDao
import com.andreromano.foodmix.database.dao.UserProfileDao
import com.andreromano.foodmix.database.model.CategoryEntity
import com.andreromano.foodmix.database.model.IngredientEntity
import com.andreromano.foodmix.database.model.UserProfileEntity


@Database(
    entities = [
        CategoryEntity::class,
        IngredientEntity::class,
        UserProfileEntity::class,
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoriesDao(): CategoriesDao
    abstract fun ingredientsDao(): IngredientsDao
    abstract fun userProfileDao(): UserProfileDao
}