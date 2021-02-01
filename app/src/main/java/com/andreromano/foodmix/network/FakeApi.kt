package com.andreromano.foodmix.network

import com.andreromano.foodmix.core.*
import com.andreromano.foodmix.domain.model.Ingredient
import com.andreromano.foodmix.domain.model.Recipe
import com.andreromano.foodmix.domain.model.RecipesOrderBy
import com.andreromano.foodmix.domain.model.Review
import com.andreromano.foodmix.network.FakeData.categories
import com.andreromano.foodmix.network.FakeData.ingredients
import com.andreromano.foodmix.network.FakeData.recipes
import com.andreromano.foodmix.network.FakeData.reviews
import com.andreromano.foodmix.network.FakeData.shouldFail
import com.andreromano.foodmix.network.FakeData.userProfile
import com.andreromano.foodmix.network.model.CategoryResult
import com.andreromano.foodmix.network.model.IngredientResult
import com.andreromano.foodmix.network.model.UserProfileResult
import kotlinx.coroutines.delay

class FakeApi : Api {

    private suspend fun <T> middleware(continuation: () -> ResultKt<T>): ResultKt<T> {
        delay(1_000)
        return if (shouldFail) ResultKt.Failure(ErrorKt.Unknown("some error message"))
        else continuation()
    }

    override suspend fun getCategories(searchQuery: String?): ResultKt<List<CategoryResult>> = middleware {
        ResultKt.Success(categories.filter { if (searchQuery != null) it.name.startsWith(searchQuery, true) else true })
    }

    override suspend fun getIngredients(searchQuery: String?): ResultKt<List<IngredientResult>> = middleware {
        ResultKt.Success(ingredients.filter { if (searchQuery != null) it.name.startsWith(searchQuery, true) else true })
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

    override suspend fun searchRecipesByIngredients(
        searchedIngredients: List<IngredientId>,
        orderBy: RecipesOrderBy
    ): ResultKt<List<Recipe>> = middleware {
        ResultKt.Success(
            recipes
                .filter { it.ingredients.all { searchedIngredients.contains(it.id) } }
                .let {
                    when (orderBy) {
                        RecipesOrderBy.RELEVANCE -> it.sortedBy { it.title }
                        RecipesOrderBy.RATING -> it.sortedBy { it.rating }
                        RecipesOrderBy.DURATION -> it.sortedBy { it.duration }
                    }
                }
        )
    }

    override suspend fun getUserProfile(): ResultKt<UserProfileResult> = middleware {
        ResultKt.Success(userProfile)
    }

}