package com.andreromano.foodmix.network

import com.andreromano.foodmix.core.CategoryId
import com.andreromano.foodmix.core.ResultFm
import com.andreromano.foodmix.domain.model.*
import com.andreromano.foodmix.network.FakeData.recipes
import com.andreromano.foodmix.network.FakeData.shouldFail

class FakeApi : Api {

    override suspend fun getRecipesByCategory(categoryId: CategoryId): ResultFm<List<Recipe>> =
        if (shouldFail) ResultFm.Failure(IllegalStateException("some error message"))
        else ResultFm.Success(recipes.filter { it.categories.any { it.id == categoryId } })

}