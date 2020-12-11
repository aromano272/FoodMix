package com.andreromano.foodmix.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.andreromano.foodmix._utils.MainCoroutineScopeRule
import com.andreromano.foodmix.core.ResultKt
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.`should be`
import org.amshove.kluent.shouldBe
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NetworkBoundResourceTest {


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()


    private lateinit var handleSaveCallResult: suspend (FooNetwork) -> Unit

    private val handleLoadFromDb = MutableSharedFlow<FooEntity?>(replay = 1)

    private lateinit var handleShouldFetch: suspend (FooEntity?) -> Boolean

    private val handleCreateCall = MutableSharedFlow<ResultKt<FooNetwork>>(replay = 1)

    private lateinit var networkBoundResource: NetworkBoundResource<FooEntity?, FooNetwork, FooDomain>

    @Before
    fun setup() {
        networkBoundResource = object : NetworkBoundResource<FooEntity?, FooNetwork, FooDomain>() {
            override suspend fun saveCallResult(result: FooNetwork) = handleSaveCallResult(result)
            override suspend fun loadFromDb(): Flow<FooEntity?> = handleLoadFromDb
            override suspend fun shouldFetch(data: FooEntity?): Boolean = handleShouldFetch(data)
            override suspend fun mapToDomain(entity: FooEntity?): FooDomain = FooDomain(entity!!.value)
            override suspend fun createCall(): ResultKt<FooNetwork> = handleCreateCall.first()
        }
    }

    @Test
    fun `asdfsdjak`() = coroutineScope.runBlockingTest {
        val dbValue = FooEntity(1)
        handleLoadFromDb.emit(dbValue)
        handleSaveCallResult = {
            // TODO: add delay to this notification
            launch {
                delay(100)
                handleLoadFromDb.emit(FooEntity(2))
            }
        }
        handleShouldFetch = { true }
        val networkValue = FooNetwork(1)
        handleCreateCall.emit(ResultKt.Success(networkValue))


        val resultFlow = networkBoundResource.asFlow().take(4).toList()
//        resultFlow.collect {
//            val a = 1
//        }
        val a = 1
    }


    private data class FooNetwork(var value: Int)
    private data class FooEntity(var value: Int)
    private data class FooDomain(var value: Int)

}