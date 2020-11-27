package com.andreromano.foodmix.data

import com.andreromano.foodmix.core.*
import com.andreromano.foodmix.data.mapper.toDomain
import com.andreromano.foodmix.data.mapper.toEntity
import com.andreromano.foodmix.database.TransactionRunner
import com.andreromano.foodmix.database.dao.CategoriesDao
import com.andreromano.foodmix.database.model.CategoryEntity
import com.andreromano.foodmix.domain.model.*
import com.andreromano.foodmix.network.Api
import com.andreromano.foodmix.network.FakeData
import com.andreromano.foodmix.network.model.CategoryResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import timber.log.Timber

class Repository(
    private val api: Api,
    private val categoriesDao: CategoriesDao,
    private val transactionRunner: TransactionRunner
) {

    private var cacheCategories: List<Category>? = null

    private suspend fun saveCacheCategories(categories: List<Category>) {
        delay(100)
        cacheCategories = categories
    }

    fun getCategoriesFlow(searchQuery: String? = null): Flow<Resource<List<Category>>> = flow {
        emit(Resource.Loading(null))
        Timber.e("emit 1")
        val dbValue = categoriesDao.getAll(searchQuery.orEmpty()).first()
        emit(Resource.Loading(dbValue.toDomain()))
        Timber.e("emit 2")

        when (val result = api.getCategories()) {
            is ResultKt.Success -> {
                // save call result
                categoriesDao.upsert(result.data.toEntity())
                // observe database
                Timber.e("emit 3")
                emitAll(categoriesDao.getAll(searchQuery.orEmpty()).map { Resource.Success(it.toDomain()) })
            }
            is ResultKt.Failure -> {
                // observe database with Failure map
                Timber.e("emit 3")
                emitAll(categoriesDao.getAll(searchQuery.orEmpty()).map { Resource.Failure(it.toDomain(), result.error) })
            }
        }
    }

    fun getCategoriesNetworkBoundResource(searchQuery: String? = null): Flow<Resource<List<Category>>> =
        object : NetworkBoundResource<List<CategoryEntity>, List<CategoryResult>, List<Category>>() {
            override suspend fun saveCallResult(result: List<CategoryResult>) = categoriesDao.replaceAll(result.toEntity())

            override fun loadFromDb(): Flow<List<CategoryEntity>> = categoriesDao.getAll(searchQuery.orEmpty())

            override suspend fun mapToDomain(entity: List<CategoryEntity>): List<Category> = entity.toDomain()

            override suspend fun createCall(): ResultKt<List<CategoryResult>> = api.getCategories()
        }.asFlow()

    fun getIngredients(searchQuery: String? = null, ingredientTypeFilter: IngredientType? = null): Flow<Resource<List<Ingredient>>> = flow {
        emit(Resource.Loading(null))
        delay(1000)
        val filteredResults = FakeData.ingredients.filter {
            val matchesSearchQuery = if (searchQuery != null) it.name.startsWith(searchQuery, true) else true
            val matchesIngredientTypeFilter = if (ingredientTypeFilter != null) it.type == ingredientTypeFilter else true
            matchesSearchQuery && matchesIngredientTypeFilter
        }
        emit(Resource.Success(filteredResults))
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

    suspend fun addFavorite(recipeId: RecipeId): ResultKt<Unit> = api.addFavorite(recipeId)

    suspend fun removeFavorite(recipeId: RecipeId): ResultKt<Unit> = api.removeFavorite(recipeId)

    suspend fun searchRecipesByIngredients(searchedIngredients: List<Ingredient>, orderBy: RecipesOrderBy) = api.searchRecipesByIngredients(searchedIngredients.map { it.id }, orderBy)
}