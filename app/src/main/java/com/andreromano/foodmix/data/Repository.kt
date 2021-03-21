package com.andreromano.foodmix.data

import com.andreromano.foodmix.core.*
import com.andreromano.foodmix.data.mapper.toDomain
import com.andreromano.foodmix.data.mapper.toEntity
import com.andreromano.foodmix.data.mapper.toNetwork
import com.andreromano.foodmix.database.TransactionRunner
import com.andreromano.foodmix.database.dao.CategoriesDao
import com.andreromano.foodmix.database.dao.IngredientTypesDao
import com.andreromano.foodmix.database.dao.IngredientsDao
import com.andreromano.foodmix.database.model.CategoryEntity
import com.andreromano.foodmix.database.model.IngredientEntity
import com.andreromano.foodmix.database.model.IngredientQuery
import com.andreromano.foodmix.database.model.IngredientTypeEntity
import com.andreromano.foodmix.domain.model.*
import com.andreromano.foodmix.network.ApiService
import com.andreromano.foodmix.network.model.CategoryResult
import com.andreromano.foodmix.network.model.IngredientResult
import com.andreromano.foodmix.network.model.RecipeResult
import com.andreromano.foodmix.network.model.ReviewResult
import kotlinx.coroutines.flow.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class Repository(
    private val api: ApiService,
    private val categoriesDao: CategoriesDao,
    private val ingredientsDao: IngredientsDao,
    private val ingredientTypesDao: IngredientTypesDao,
    private val transactionRunner: TransactionRunner
) {

    fun getCategories(searchQuery: String? = null): Flow<Resource<List<Category>>> =
        object : NetworkBoundResource<List<CategoryEntity>, List<CategoryResult>, List<Category>>() {
            override suspend fun saveCallResult(result: List<CategoryResult>) = categoriesDao.replaceAll(result.map { it.toEntity() })

            override suspend fun loadFromDb(): Flow<List<CategoryEntity>> = categoriesDao.getAll(searchQuery.orEmpty())

            override suspend fun mapToDomain(entity: List<CategoryEntity>): List<Category> = entity.map { it.toDomain() }

            override suspend fun createCall(): ResultKt<List<CategoryResult>> = api.getCategories(searchQuery)
        }.asFlow()

    fun getIngredientTypes(): Flow<Resource<List<IngredientType>>> =
        object : NetworkBoundResource<List<IngredientTypeEntity>, List<String>, List<IngredientType>>() {
            override suspend fun saveCallResult(result: List<String>) = ingredientTypesDao.replaceAll(result.map { IngredientTypeEntity(it, it) })

            override suspend fun loadFromDb(): Flow<List<IngredientTypeEntity>> = ingredientTypesDao.getAll()

            override suspend fun mapToDomain(entity: List<IngredientTypeEntity>): List<IngredientType> = entity.map { it.toDomain() }

            override suspend fun createCall(): ResultKt<List<String>> = api.getIngredientTypes()
        }.asFlow()

    fun getIngredients(searchQuery: String? = null, ingredientTypeFilter: IngredientType? = null): Flow<Resource<List<Ingredient>>> =
        object : NetworkBoundResource<List<IngredientQuery>, List<IngredientResult>, List<Ingredient>>(FetchStrategy.IF_LOCAL_IS_NULL) {
            override suspend fun saveCallResult(result: List<IngredientResult>) {
                transactionRunner.run {
                    ingredientTypesDao.insertOrIgnore(result.map { it.type }.distinct().map { IngredientTypeEntity(it, it) })
                    ingredientsDao.replaceAll(result.map { it.toEntity() })
                }
            }

            override suspend fun loadFromDb(): Flow<List<IngredientQuery>> = ingredientsDao.getAll(searchQuery.orEmpty(), ingredientTypeFilter?.id)

            override suspend fun mapToDomain(entity: List<IngredientQuery>): List<Ingredient> = entity.map { it.toDomain() }

            override suspend fun createCall(): ResultKt<List<IngredientResult>> = api.getIngredients()
        }.asFlow()

    suspend fun getRecipes(category: Category): ResultKt<List<Recipe>> = api.getRecipesByCategory(category.id).mapData { it.map { it.toDomain() } }

    suspend fun searchRecipesByIngredients(searchedIngredients: List<Ingredient>, orderBy: RecipesOrderBy): ResultKt<List<Recipe>> =
        api.searchRecipesByIngredients(searchedIngredients.map { it.id }, orderBy.toNetwork())
            .mapData { it.map { it.toDomain() } }

    suspend fun getRecipe(recipeId: RecipeId): ResultKt<Recipe> = api.getRecipe(recipeId).mapData { it.toDomain() }

    suspend fun createRecipe(request: CreateRecipeRequest): ResultKt<Recipe> =
        api.createRecipeMultipart(request.toNetwork()).mapData { it.toDomain() }

    suspend fun sendReview(review: String): ResultKt<Review> = api.sendReview(review).mapData { it.toDomain() }

    suspend fun addIngredientToShoppingList(ingredient: IngredientId): ResultKt<Unit> = api.addIngredientToShoppingList(ingredient)

    suspend fun addFavorite(recipeId: RecipeId): ResultKt<Unit> = api.addFavorite(recipeId)

    suspend fun removeFavorite(recipeId: RecipeId): ResultKt<Unit> = api.removeFavorite(recipeId)

}