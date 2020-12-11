package com.andreromano.foodmix.network

import com.andreromano.foodmix.core.CategoryId
import com.andreromano.foodmix.core.IngredientId
import com.andreromano.foodmix.core.RecipeId
import com.andreromano.foodmix.core.ResultKt
import com.andreromano.foodmix.domain.model.Ingredient
import com.andreromano.foodmix.domain.model.Recipe
import com.andreromano.foodmix.domain.model.RecipesOrderBy
import com.andreromano.foodmix.domain.model.Review
import com.andreromano.foodmix.network.model.CategoryResult
import com.andreromano.foodmix.network.model.IngredientResult
import retrofit2.http.*

interface Api {

    @GET("2afb42e8-3681-42cf-aad7-8c214171ecf1")
    suspend fun getCategories(@Query("searchQuery") searchQuery: String?): ResultKt<List<CategoryResult>>

    @GET("ingredients")
    suspend fun getIngredients(@Query("searchQuery") searchQuery: String?): ResultKt<List<IngredientResult>>

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

    @GET("search_recipes_by_ingredients")
    suspend fun searchRecipesByIngredients(
        @Query("ingredients") searchedIngredients: List<IngredientId>,
        @Query("order_by") orderBy: RecipesOrderBy
    ): ResultKt<List<Recipe>>
}