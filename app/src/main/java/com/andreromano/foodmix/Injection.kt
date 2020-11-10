package com.andreromano.foodmix

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import androidx.room.withTransaction
import com.andreromano.foodmix.data.Repository
import com.andreromano.foodmix.database.AppDatabase
import com.andreromano.foodmix.database.TransactionRunner
import com.andreromano.foodmix.network.Api
import com.andreromano.foodmix.network.FakeApi
import com.andreromano.foodmix.network.FromBaseResponseToResultKtAdapterFactory
import com.andreromano.foodmix.network.ServiceGenerator
import com.andreromano.foodmix.ui.utils.NetworkUtils
import com.squareup.moshi.Moshi

object Injection {

    private var repository: Repository? = null
    fun provideRepository(context: Context) = repository ?: Repository(
        provideApi(),
        provideAppDatabase(context).categoriesDao(),
        provideTransactionRunner(context)
    ).also {
        repository = it
    }

    private var api: Api? = null
    fun provideApi(): Api = FakeApi() ?: ServiceGenerator(provideMoshi()).createService(Api::class.java).also {
        api = it
    }

    private var moshi: Moshi? = null
    fun provideMoshi(): Moshi = moshi ?: Moshi.Builder()
        .add(FromBaseResponseToResultKtAdapterFactory())
        .build().also {
            moshi = it
        }

    private var appDatabase: AppDatabase? = null
    fun provideAppDatabase(context: Context): AppDatabase =
        appDatabase ?: Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "foodmix").build().also {
            appDatabase = it
        }

    fun provideTransactionRunner(context: Context): TransactionRunner {
        val db = provideAppDatabase(context)
        return object : TransactionRunner {
            override suspend fun run(block: suspend () -> Unit) {
                db.withTransaction { block() }
            }
        }
    }

    fun providerNetworkUtils(context: Context): NetworkUtils =
        NetworkUtils(context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
}