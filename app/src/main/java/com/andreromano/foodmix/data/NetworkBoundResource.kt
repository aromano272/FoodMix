package com.andreromano.foodmix.data

import com.andreromano.foodmix.core.CombineResult
import com.andreromano.foodmix.core.ErrorKt
import com.andreromano.foodmix.core.Resource
import com.andreromano.foodmix.core.ResultKt
import com.andreromano.foodmix.extensions.startWithPreEmission
import kotlinx.coroutines.flow.*
import timber.log.Timber


abstract class NetworkBoundResource<EntityModel : Any?, NetworkModel : Any, DomainModel : Any>(
    private val fetchStrategy: FetchStrategy = FetchStrategy.ALWAYS
) {

    fun asFlow(): Flow<Resource<DomainModel>> = flow {
        Timber.e("boomshakalaka flow {}")

        emit(Resource.Loading(null))
        val shouldFetch = shouldFetch(loadFromDb().first())
        val finalFlow = if (shouldFetch) {
            val resultFlow = ::fetchFromNetworkAndSaveCallResult.asFlow()

            combine(loadFromDb(), resultFlow.startWithPreEmission()) { dbValue, result ->
                Timber.e("boomshakalaka combine $dbValue, $result")
                when {
                    result is CombineResult.ToBeEmitted -> Resource.Loading(mapToDomainInternal(dbValue))
                    result is CombineResult.Emission -> when (result.data) {
                        is ResultKt.Success -> if (dbValue != null) Resource.Success(mapToDomain(dbValue)) else Resource.Failure(null, ErrorKt.NotFound)
                        is ResultKt.Failure -> Resource.Failure(mapToDomainInternal(dbValue), result.data.error)
                    }
                    else -> throw IllegalStateException()
                }
            }
        } else {
            loadFromDb().mapLatest { dbValue ->
                if (dbValue != null) Resource.Success(mapToDomain(dbValue)) else Resource.Failure(null, ErrorKt.NotFound)
            }
        }.distinctUntilChanged()

        emitAll(finalFlow)
    }

    private suspend fun fetchFromNetworkAndSaveCallResult(): ResultKt<NetworkModel> = createCall().also {
        Timber.e("boomshakalaka fetchFromNetwork")
        if (it is ResultKt.Success) saveCallResult(it.data)
    }

    protected abstract suspend fun saveCallResult(result: NetworkModel)

    protected abstract fun loadFromDb(): Flow<EntityModel>

    protected open suspend fun shouldFetch(data: EntityModel): Boolean = when (fetchStrategy) {
        FetchStrategy.ALWAYS -> true
        FetchStrategy.NEVER -> false
        FetchStrategy.IF_LOCAL_IS_NULL -> data == null || (data is Collection<*> && data.isEmpty())
    }

    protected abstract suspend fun mapToDomain(entity: EntityModel): DomainModel

    private suspend fun mapToDomainInternal(entity: EntityModel): DomainModel? = entity?.let { mapToDomain(it) }

    protected abstract suspend fun createCall(): ResultKt<NetworkModel>
}

enum class FetchStrategy {
    ALWAYS,
    NEVER,
    IF_LOCAL_IS_NULL
}