package com.andreromano.foodmix.network

import android.net.Uri
import androidx.core.net.toFile
import com.andreromano.foodmix.core.CategoryId
import com.andreromano.foodmix.core.IngredientId
import com.andreromano.foodmix.core.RecipeId
import com.andreromano.foodmix.core.ResultKt
import com.andreromano.foodmix.network.model.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.http.*

class ApiService(
    private val api: Api
) {

    suspend fun getCategories(searchQuery: String?): ResultKt<List<CategoryResult>> = api.getCategories(searchQuery)

    suspend fun getIngredientTypes(): ResultKt<List<String>> = api.getIngredientTypes()

    suspend fun getIngredients(): ResultKt<List<IngredientResult>> = api.getIngredients()

    suspend fun getRecipesByCategory(categoryId: CategoryId): ResultKt<List<RecipeResult>> = api.getRecipesByCategory(categoryId)

    suspend fun searchRecipesByIngredients(
        searchedIngredients: List<IngredientId>,
        orderBy: RecipesOrderByRequest
    ): ResultKt<List<RecipeResult>> = api.searchRecipesByIngredients(searchedIngredients, orderBy)

    suspend fun getRecipe(recipeId: RecipeId): ResultKt<RecipeResult> = api.getRecipe(recipeId)

    suspend fun updateRecipeImage(image: MultipartBody.Part): ResultKt<String> = api.updateRecipeImage(image)

    suspend fun updateRecipeDirectionImages(directionIndexToImage: Map<Int, Uri?>): ResultKt<String> = api.updateRecipeDirectionImages(directionIndexToImage.map { (index, uri) ->
        if (uri != null) {
            val file = uri.toFile()
            MultipartBody.Part.createFormData(
                "image[$index]",
                file.name,
                file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            )
        } else {
            MultipartBody.Part.createFormData(
                "image[$index]",
                ""
            )
        }
    })

    suspend fun createRecipeMultipart(request: CreateRecipeRequest): ResultKt<RecipeResult> = api.createRecipeMultipart(
        request.title,
        request.description,
        request.image?.toMultipart("image"),
        request.categories.toMultipart("category"),
        request.cookingTime,
        request.servingsCount,
        request.calories,
        request.ingredients.toMultipart("ingredient"),
        request.directions.flatMapIndexed { index, direction ->
            listOfNotNull(
                MultipartBody.Part.createFormData("direction.title[$index]", direction.title),
                MultipartBody.Part.createFormData("direction.description[$index]", direction.description),
                direction.image?.toMultipart("direction.image[$index]"),
            )
        },
    )

    suspend fun sendReview(review: String): ResultKt<ReviewResult> = api.sendReview(review)

    suspend fun addIngredientToShoppingList(ingredient: IngredientId): ResultKt<Unit> = api.addIngredientToShoppingList(ingredient)

    suspend fun addFavorite(recipeId: RecipeId): ResultKt<Unit> = api.addFavorite(recipeId)

    suspend fun removeFavorite(recipeId: RecipeId): ResultKt<Unit> = api.removeFavorite(recipeId)

    suspend fun getUserProfile(): ResultKt<UserProfileResult> = api.getUserProfile()

    private fun Uri.toMultipart(name: String): MultipartBody.Part = MultipartBody.Part.createFormData(
        name,
        this.toFile().name,
        this.toFile().asRequestBody("multipart/form-data".toMediaTypeOrNull())
    )

//    private fun String.toMultipart(name: String): MultipartBody.Part = MultipartBody.Part.createFormData(
//        name,
//        this.toFile().name,
//        this.toFile().asRequestBody("multipart/form-data".toMediaTypeOrNull())
//    )

    private fun List<Any>.toMultipart(name: String): List<MultipartBody.Part> =
        this.mapIndexed { index, value ->
            MultipartBody.Part.createFormData("$name[$index]", "$value")
        }

}