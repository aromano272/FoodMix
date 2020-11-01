package com.andreromano.foodmix

import android.content.Context
import com.andreromano.foodmix.data.Repository
import com.andreromano.foodmix.network.Api
import com.andreromano.foodmix.network.FakeApi
import com.andreromano.foodmix.network.FromBaseResponseToResultKtAdapterFactory
import com.andreromano.foodmix.network.ServiceGenerator
import com.squareup.moshi.Moshi

object Injection {

    private var repository: Repository? = null
    fun provideRepository(context: Context) = repository ?: Repository(
//        provideApi()
        FakeApi()
    ).also {
        repository = it
    }

    private var api: Api? = null
    fun provideApi(): Api = api ?: ServiceGenerator(provideMoshi()).createService(Api::class.java).also {
        api = it
    }

    private var moshi: Moshi? = null
    fun provideMoshi(): Moshi = moshi ?: Moshi.Builder()
        .add(FromBaseResponseToResultKtAdapterFactory())
        .build().also {
        moshi = it
    }
}