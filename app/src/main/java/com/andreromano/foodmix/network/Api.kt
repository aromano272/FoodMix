package com.andreromano.foodmix.network

import com.andreromano.foodmix.core.CategoryId
import com.andreromano.foodmix.domain.model.Ingredient
import com.andreromano.foodmix.core.RecipeId
import com.andreromano.foodmix.core.ResultKt
import com.andreromano.foodmix.domain.model.Category
import com.andreromano.foodmix.domain.model.Recipe
import com.andreromano.foodmix.domain.model.Review
import com.andreromano.foodmix.network.model.CategoryResult
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {

    @GET("2afb42e8-3681-42cf-aad7-8c214171ecf1")
    suspend fun getCategories(): ResultKt<List<CategoryResult>>

    @GET("recipe/category/{categoryId}")
    suspend fun getRecipesByCategory(@Path("categoryId") categoryId: CategoryId): ResultKt<List<Recipe>>

    @GET("recipe/{recipeId}")
    suspend fun getRecipe(@Path("recipeId") recipeId: RecipeId): ResultKt<Recipe>

    @POST("send_review")
    suspend fun sendReview(@Field("review") review: String): ResultKt<Review>

    @GET("add_to_shopping_list")
    suspend fun addIngredientToShoppingList(ingredient: Ingredient): ResultKt<Unit>

    @POST("add_favorite")
    suspend fun addFavorite(recipeId: RecipeId): ResultKt<Unit>

    @POST("remove_favorite")
    suspend fun removeFavorite(recipeId: RecipeId): ResultKt<Unit>

}