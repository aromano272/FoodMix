package com.andreromano.foodmix.network

import com.andreromano.foodmix.core.*
import com.andreromano.foodmix.network.FakeData.categories
import com.andreromano.foodmix.network.FakeData.ingredientTypes
import com.andreromano.foodmix.network.FakeData.ingredients
import com.andreromano.foodmix.network.FakeData.recipes
import com.andreromano.foodmix.network.FakeData.reviews
import com.andreromano.foodmix.network.FakeData.shouldFail
import com.andreromano.foodmix.network.FakeData.userProfile
import com.andreromano.foodmix.network.model.*
import kotlinx.coroutines.delay
import okhttp3.MultipartBody
import retrofit2.http.Part

class FakeApi : Api {

    private suspend fun <T> middleware(continuation: () -> ResultKt<T>): ResultKt<T> {
        delay(1_000)
        return if (shouldFail) ResultKt.Failure(ErrorKt.Unknown("some error message"))
        else continuation()
    }

    override suspend fun getCategories(searchQuery: String?): ResultKt<List<CategoryResult>> = middleware {
        ResultKt.Success(categories.filter { if (searchQuery != null) it.name.startsWith(searchQuery, true) else true })
    }

    override suspend fun getIngredientTypes(): ResultKt<List<String>> = middleware {
        ResultKt.Success(ingredientTypes)
    }

    override suspend fun getIngredients(searchQuery: String?): ResultKt<List<IngredientResult>> = middleware {
        ResultKt.Success(ingredients.filter { if (searchQuery != null) it.name.startsWith(searchQuery, true) else true })
    }

    override suspend fun getRecipesByCategory(categoryId: CategoryId): ResultKt<List<RecipeResult>> = middleware {
        ResultKt.Success(recipes.filter { it.categories.any { it.id == categoryId } })
    }

    override suspend fun searchRecipesByIngredients(
        searchedIngredients: List<IngredientId>,
        orderBy: RecipesOrderByRequest
    ): ResultKt<List<RecipeResult>> = middleware {
        ResultKt.Success(
            recipes
                .filter { it.ingredients.all { searchedIngredients.contains(it.id) } }
                .let {
                    when (orderBy) {
                        RecipesOrderByRequest.RELEVANCE -> it.sortedBy { it.title }
                        RecipesOrderByRequest.RATING -> it.sortedBy { it.rating }
                        RecipesOrderByRequest.DURATION -> it.sortedBy { it.cookingTime }
                    }
                }
        )
    }

    override suspend fun getRecipe(recipeId: RecipeId): ResultKt<RecipeResult> = middleware {
        val recipe = recipes.find { it.id == recipeId }
        if (recipe != null) ResultKt.Success(recipe)
        else ResultKt.Failure(ErrorKt.Generic)
    }

    override suspend fun updateRecipeImage(@Part image: MultipartBody.Part): ResultKt<String> = middleware {
        ResultKt.Success("imageurl")
    }

    override suspend fun updateRecipeDirectionImages(@Part images: List<MultipartBody.Part?>): ResultKt<String> = middleware {
        ResultKt.Success("imageurl")
    }

    override suspend fun createRecipeMultipart(
        title: String,
        description: String,
        image: MultipartBody.Part?,
        categories: List<MultipartBody.Part>,
        cookingTime: Minutes,
        servingsCount: Int,
        calories: Int,
        ingredients: List<MultipartBody.Part>,
        directions: List<MultipartBody.Part>,
    ): ResultKt<RecipeResult> = middleware {
        TODO("Not yet implemented")
    }

    override suspend fun sendReview(review: String): ResultKt<ReviewResult> = middleware {
        ResultKt.Success(reviews.first())
    }

    override suspend fun addIngredientToShoppingList(ingredient: IngredientId): ResultKt<Unit> = middleware {
        ResultKt.Success(Unit)
    }

    override suspend fun addFavorite(recipeId: RecipeId): ResultKt<Unit> = middleware {
        ResultKt.Success(Unit)
    }

    override suspend fun removeFavorite(recipeId: RecipeId): ResultKt<Unit> = middleware {
        ResultKt.Success(Unit)
    }

    override suspend fun getUserProfile(): ResultKt<UserProfileResult> = middleware {
        ResultKt.Success(userProfile)
    }

}