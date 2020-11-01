package com.andreromano.foodmix.data

import com.andreromano.foodmix.core.Ingredient
import com.andreromano.foodmix.core.RecipeId
import com.andreromano.foodmix.core.ResultKt
import com.andreromano.foodmix.data.mapper.toDomain
import com.andreromano.foodmix.domain.model.Category
import com.andreromano.foodmix.domain.model.Recipe
import com.andreromano.foodmix.domain.model.Review
import com.andreromano.foodmix.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class Repository(
    private val api: Api
) {

    private var cacheCategories: List<Category>? = null

    private suspend fun saveCacheCategories(categories: List<Category>) {
        delay(100)
        cacheCategories = categories
    }

    suspend fun getCategories(searchQuery: String? = null): ResultKt<List<Category>> {
        val cacheCategories = cacheCategories
        val result = if (cacheCategories != null) {
            ResultKt.Success(cacheCategories)
        } else {
            val result = api.getCategories().mapData { it.toDomain() }
            if (result is ResultKt.Success) saveCacheCategories(result.data)
            result
        }

        return result.mapData {
            if (searchQuery.isNullOrEmpty()) return@mapData it
            withContext(Dispatchers.Default) {
                it.filter { it.name.startsWith(searchQuery) }
            }
        }
    }

    suspend fun getRecipes(category: Category): ResultKt<List<Recipe>> = api.getRecipesByCategory(category.id)

    suspend fun getRecipe(recipeId: RecipeId): ResultKt<Recipe> = api.getRecipe(recipeId)

    suspend fun sendReview(review: String): ResultKt<Review> = api.sendReview(review)

    suspend fun addIngredientToShoppingList(ingredient: Ingredient): ResultKt<Unit> = api.addIngredientToShoppingList(ingredient)

}