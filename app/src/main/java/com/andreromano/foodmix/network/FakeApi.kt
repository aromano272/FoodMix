package com.andreromano.foodmix.network

import com.andreromano.foodmix.core.*
import com.andreromano.foodmix.domain.model.*
import com.andreromano.foodmix.network.FakeData.categories
import com.andreromano.foodmix.network.FakeData.recipes
import com.andreromano.foodmix.network.FakeData.reviews
import com.andreromano.foodmix.network.FakeData.shouldFail
import com.andreromano.foodmix.network.model.CategoryResult
import kotlinx.coroutines.delay

class FakeApi : Api {

    private suspend fun <T> middleware(continuation: () -> ResultKt<T>): ResultKt<T> {
        delay(1_000)
        return if (shouldFail) ResultKt.Failure(ErrorKt.Unknown("some error message"))
        else continuation()
    }

    override suspend fun getCategories(): ResultKt<List<CategoryResult>> = middleware {
        ResultKt.Success(categories)
    }

    override suspend fun getRecipesByCategory(categoryId: CategoryId): ResultKt<List<Recipe>> = middleware {
        ResultKt.Success(recipes.filter { it.categories.any { it.id == categoryId } })
    }

    override suspend fun getRecipe(recipeId: RecipeId): ResultKt<Recipe> = middleware {
        val recipe = recipes.find { it.id == recipeId }
        if (recipe != null) ResultKt.Success(recipe)
        else ResultKt.Failure(ErrorKt.Generic)
    }

    override suspend fun sendReview(review: String): ResultKt<Review> = middleware {
        ResultKt.Success(reviews.first())
    }

    override suspend fun addIngredientToShoppingList(ingredient: Ingredient): ResultKt<Unit> = middleware {
        ResultKt.Success(Unit)
    }

    override suspend fun addFavorite(recipeId: RecipeId): ResultKt<Unit> = middleware {
        ResultKt.Success(Unit)
    }

    override suspend fun removeFavorite(recipeId: RecipeId): ResultKt<Unit> = middleware {
        ResultKt.Success(Unit)
    }
}