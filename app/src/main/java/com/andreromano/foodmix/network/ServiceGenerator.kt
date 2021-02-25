package com.andreromano.foodmix.network


import android.os.Build
import com.andreromano.foodmix.network.mapper.ResultKtCallAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber

class ServiceGenerator(
    private val moshi: Moshi
) {

    companion object {
        const val TIMEOUT_SECONDS = "TIMEOUT_SECONDS"
        const val ACCEPT_LANGUAGE = "Accept-Language"
    }

    private val retrofitBuilder by lazy {
        Retrofit.Builder()
//            .baseUrl("https://run.mocky.io/v3/")
            .baseUrl(if (Build.FINGERPRINT.contains("generic")) "http://10.0.2.2:8080/" else "http://127.0.0.1:8080/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(ResultKtCallAdapterFactory())
    }

    private fun createHttpClient() = OkHttpClient.Builder()

    fun <S> createService(serviceClass: Class<S>): S {
        val httpBuilder = createHttpClient()

        httpBuilder.addInterceptor(
            HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Timber.tag("OkHttp").d(message)
                }
            }).apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )

        val retrofit = retrofitBuilder
            .client(httpBuilder.build())
            .build()

        return retrofit.create(serviceClass)
    }

}