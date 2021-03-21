package com.andreromano.foodmix.network

import com.andreromano.foodmix.core.*
import com.andreromano.foodmix.network.model.*
import okhttp3.MultipartBody
import retrofit2.http.*

interface Api {

    @GET("categories")
    suspend fun getCategories(@Query("searchQuery") searchQuery: String?): ResultKt<List<CategoryResult>>

    @GET("ingredients/types")
    suspend fun getIngredientTypes(): ResultKt<List<String>>

    @GET("ingredients")
    suspend fun getIngredients(@Query("searchQuery") searchQuery: String? = null): ResultKt<List<IngredientResult>>

    @GET("recipes/category/{categoryId}")
    suspend fun getRecipesByCategory(@Path("categoryId") categoryId: CategoryId): ResultKt<List<RecipeResult>>

    @GET("recipes/ingredients")
    suspend fun searchRecipesByIngredients(
        @Query("ingredients") searchedIngredients: List<IngredientId>,
        @Query("order_by") orderBy: RecipesOrderByRequest
    ): ResultKt<List<RecipeResult>>

    @GET("recipes/{recipeId}")
    suspend fun getRecipe(@Path("recipeId") recipeId: RecipeId): ResultKt<RecipeResult>

    @POST("todo")
    @Multipart
    suspend fun updateRecipeImage(@Part image: MultipartBody.Part): ResultKt<String>

    @POST("todos")
    @Multipart
    suspend fun updateRecipeDirectionImages(@Part images: List<MultipartBody.Part?>): ResultKt<String>

    @POST("recipes")
    @Multipart
    suspend fun createRecipeMultipart(
        @Part("title") title: String,
        @Part("description") description: String,
        @Part image: MultipartBody.Part?,
        @Part categories: List<MultipartBody.Part>,
        @Part("cookingTime") cookingTime: Minutes,
        @Part("servingsCount") servingsCount: Int,
        @Part("calories") calories: Int,
        @Part ingredients: List<MultipartBody.Part>,
        @Part directions: List<MultipartBody.Part>,
    ): ResultKt<RecipeResult>

    @POST("send_review")
    suspend fun sendReview(@Field("review") review: String): ResultKt<ReviewResult>

    @GET("add_to_shopping_list")
    suspend fun addIngredientToShoppingList(ingredient: IngredientId): ResultKt<Unit>

    @POST("add_favorite")
    suspend fun addFavorite(recipeId: RecipeId): ResultKt<Unit>

    @POST("remove_favorite")
    suspend fun removeFavorite(recipeId: RecipeId): ResultKt<Unit>

    @GET("user_profile")
    suspend fun getUserProfile(): ResultKt<UserProfileResult>

}