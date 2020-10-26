package com.andreromano.foodmix.network

import com.andreromano.foodmix.core.CategoryId
import com.andreromano.foodmix.core.ResultFm
import com.andreromano.foodmix.domain.model.Recipe
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("recipe/category/{categoryId}")
    suspend fun getRecipesByCategory(@Path("categoryId") categoryId: CategoryId): ResultFm<List<Recipe>>

}