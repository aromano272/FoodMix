package com.andreromano.foodmix.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.andreromano.foodmix.database.model.UserProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UserProfileDao : BaseDao<UserProfileEntity>() {

    @Query("SELECT * FROM userprofileentity")
    abstract fun get(): Flow<UserProfileEntity?>

}