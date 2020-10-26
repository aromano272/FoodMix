package com.andreromano.foodmix

import android.content.Context
import com.andreromano.foodmix.data.RecipeRepository
import com.andreromano.foodmix.network.Api
import com.andreromano.foodmix.network.FakeApi
import com.andreromano.foodmix.network.ServiceGenerator
import com.squareup.moshi.Moshi

object Injection {

    private var recipeRepository: RecipeRepository? = null
    fun provideRecipeRepository(context: Context) = recipeRepository ?: RecipeRepository(
//        provideApi()
        FakeApi()
    ).also {
        recipeRepository = it
    }

    private var api: Api? = null
    fun provideApi(): Api = api ?: ServiceGenerator(provideMoshi()).createService(Api::class.java).also {
        api = it
    }

    private var moshi: Moshi? = null
    fun provideMoshi(): Moshi = moshi ?: Moshi.Builder().build().also {
        moshi = it
    }
}