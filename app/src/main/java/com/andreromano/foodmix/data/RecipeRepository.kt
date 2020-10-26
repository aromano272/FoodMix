package com.andreromano.foodmix.data

import com.andreromano.foodmix.core.ResultFm
import com.andreromano.foodmix.domain.model.Category
import com.andreromano.foodmix.domain.model.Recipe
import com.andreromano.foodmix.network.Api

class RecipeRepository(
    private val api: Api
) {

    suspend fun getRecipes(category: Category): ResultFm<List<Recipe>> = api.getRecipesByCategory(category.id)

}