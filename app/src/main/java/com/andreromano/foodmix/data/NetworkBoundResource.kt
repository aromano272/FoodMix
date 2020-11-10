package com.andreromano.foodmix.data

import com.andreromano.foodmix.core.ErrorKt
import com.andreromano.foodmix.core.Resource
import com.andreromano.foodmix.core.ResultKt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

sealed class CombineResult<out T> {
    object ToBeEmitted : CombineResult<Nothing>()
    data class Emission<out T>(val data: T) : CombineResult<T>()

    fun orNull(): T? = when (this) {
        is ToBeEmitted -> null
        is Emission -> this.data
    }
}

private fun <T> Flow<T>.startWithPreEmission(): Flow<CombineResult<T>> = mapLatest { CombineResult.Emission(it) as CombineResult<T> }.onStart { emit(CombineResult.ToBeEmitted) }

abstract class NetworkBoundResource<EntityModel : Any?, NetworkModel : Any, DomainModel : Any>(
    private val fetchStrategy: FetchStrategy = FetchStrategy.ALWAYS
) {

//    private val result: MutableStateFlow<Resource<DomainModel>> = MutableStateFlow(Resource.Loading(null))

    init {
//        result.
//        val dbValue = loadFromDb().first()
    }

//    fun asFlow1(): Flow<Resource<DomainModel>> = flow {
//        emit(Resource.Loading(null))
//        val dbValue = loadFromDb().first()
//        if (shouldFetch(dbValue)) {
//            loadFromDb().startWithPreEmission().combine(::createCall.asFlow().startWithPreEmission()) { dbValue, result ->
//                if (dbValue)
//            }
//            emit(Resource.Loading(mapToDomainInternal(dbValue)))
//            val result = createCall()
//
//            loadFromDb().combine(createCall())
//
//        } else {
//            emitAll(loadFromDb().mapLatest { newData ->
//                if (newData != null) Resource.Success(mapToDomain(newData))
//                else Resource.Failure(null, ErrorKt.NotFound)
//            })
//        }
//    }

    fun asFlow(): Flow<Resource<DomainModel>> = flow {
//        val resultFlow = ::fetchFromNetworkAndSaveCallResult.asFlow()
        Timber.e("boomshakalaka flow {}")

        emit(Resource.Loading(null))
        val shouldFetch = shouldFetch(loadFromDb().first())
        val finalFlow = if (shouldFetch) {
            val resultFlow = ::fetchFromNetworkAndSaveCallResult.asFlow()

            loadFromDb().combine(resultFlow.startWithPreEmission()) { dbValue, result ->
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


//        val finalFlow = loadFromDb().startWithPreEmission().combine(resultFlow.startWithPreEmission()) { dbValue, result ->
//            Timber.e("boomshakalaka combine $dbValue, $result")
//            when {
//                dbValue is CombineResult.ToBeEmitted -> Resource.Loading(null)
//                dbValue is CombineResult.Emission && result is CombineResult.ToBeEmitted -> Resource.Loading(mapToDomain(dbValue.data))
//                dbValue is CombineResult.Emission && result is CombineResult.Emission -> when (result.data) {
//                    is ResultKt.Success -> Resource.Success(mapToDomain(dbValue.data))
//                    is ResultKt.Failure -> Resource.Failure(mapToDomainInternal(dbValue.data), result.data.error)
//                }
//                else -> throw IllegalStateException()
//            }
//        }.distinctUntilChanged()

        emitAll(finalFlow)
    }

//    private lateinit var scope: CoroutineScope

    private suspend fun fetchFromNetworkAndSaveCallResult(): ResultKt<NetworkModel> = createCall().also {
        Timber.e("boomshakalaka fetchFromNetwork")
        if (it is ResultKt.Success) saveCallResult(it.data)
    }


//    private fun fetchFromNetwork(): Flow<Resource<DomainModel>> {
//        val shared = MutableSharedFlow<Resource<DomainModel>>()
//
//        val result = loadFromDb().startWithPreEmission().combine(::createCall.asFlow().startWithPreEmission()) { dbValue, result ->
//
//        }
//
//        scope.launch {
//            result.collect {
//                shared.tryEmit()
//            }
//        }
//
//        loadFromDb().startWithPreEmission().combine(::createCall.asFlow().startWithPreEmission()) { dbValue, result ->
//
//        }
//
//        return shared
//    }

//    fun asFlow(): Flow<Resource<DomainModel>> = flow {
//        emit(Resource.Loading(null))
//        val dbValue = loadFromDb().first()
//        if (shouldFetch(dbValue)) {
//            emit(Resource.Loading(mapToDomainInternal(dbValue)))
//            val result = createCall()
//
//            loadFromDb().combine(createCall())
//
//        } else {
//            emitAll(loadFromDb().mapLatest { newData ->
//                if (newData != null) Resource.Success(mapToDomain(newData))
//                else Resource.Failure(null, ErrorKt.NotFound)
//            })
//        }
//    }

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