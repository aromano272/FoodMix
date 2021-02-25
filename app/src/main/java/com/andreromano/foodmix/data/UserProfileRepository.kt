package com.andreromano.foodmix.data

import com.andreromano.foodmix.core.Resource
import com.andreromano.foodmix.core.ResultKt
import com.andreromano.foodmix.data.mapper.toDomain
import com.andreromano.foodmix.data.mapper.toEntity
import com.andreromano.foodmix.database.dao.UserProfileDao
import com.andreromano.foodmix.database.model.UserProfileEntity
import com.andreromano.foodmix.domain.model.UserProfile
import com.andreromano.foodmix.network.ApiService
import com.andreromano.foodmix.network.model.UserProfileResult
import kotlinx.coroutines.flow.Flow

class UserProfileRepository(
    private val api: ApiService,
    private val userProfileDao: UserProfileDao,
) {

    fun get(): Flow<Resource<UserProfile>> =
        object : NetworkBoundResource<UserProfileEntity?, UserProfileResult, UserProfile>() {
            override suspend fun saveCallResult(result: UserProfileResult) = userProfileDao.upsert(result.toEntity())

            override suspend fun loadFromDb(): Flow<UserProfileEntity?> = userProfileDao.get()

            override suspend fun mapToDomain(entity: UserProfileEntity?): UserProfile = entity!!.toDomain()

            override suspend fun createCall(): ResultKt<UserProfileResult> = api.getUserProfile()
        }.asFlow()
}