package com.andreromano.foodmix.data

import com.andreromano.foodmix.core.*
import com.andreromano.foodmix.data.mapper.toDomain
import com.andreromano.foodmix.data.mapper.toEntity
import com.andreromano.foodmix.database.TransactionRunner
import com.andreromano.foodmix.database.dao.CategoriesDao
import com.andreromano.foodmix.database.dao.IngredientsDao
import com.andreromano.foodmix.database.model.CategoryEntity
import com.andreromano.foodmix.database.model.IngredientEntity
import com.andreromano.foodmix.domain.model.*
import com.andreromano.foodmix.network.Api
import com.andreromano.foodmix.network.model.CategoryResult
import com.andreromano.foodmix.network.model.IngredientResult
import kotlinx.coroutines.flow.*

class Repository(
    private val api: Api,
    private val categoriesDao: CategoriesDao,
    private val ingredientsDao: IngredientsDao,
    private val transactionRunner: TransactionRunner
) {

    fun getCategories(searchQuery: String? = null): Flow<Resource<List<Category>>> =
        object : NetworkBoundResource<List<CategoryEntity>, List<CategoryResult>, List<Category>>() {
            override suspend fun saveCallResult(result: List<CategoryResult>) = categoriesDao.replaceAll(result.map { it.toEntity() })

            override suspend fun loadFromDb(): Flow<List<CategoryEntity>> = categoriesDao.getAll(searchQuery.orEmpty())

            override suspend fun mapToDomain(entity: List<CategoryEntity>): List<Category> = entity.map { it.toDomain() }

            override suspend fun createCall(): ResultKt<List<CategoryResult>> = api.getCategories(searchQuery)
        }.asFlow()

    fun getIngredients(searchQuery: String? = null, ingredientTypeFilter: IngredientType? = null): Flow<Resource<List<Ingredient>>> =
        object : NetworkBoundResource<List<IngredientEntity>, List<IngredientResult>, List<Ingredient>>() {
            override suspend fun saveCallResult(result: List<IngredientResult>) = ingredientsDao.replaceAll(result.map { it.toEntity() })

            override suspend fun loadFromDb(): Flow<List<IngredientEntity>> = ingredientsDao.getAll(searchQuery.orEmpty(), ingredientTypeFilter?.toEntity())

            override suspend fun mapToDomain(entity: List<IngredientEntity>): List<Ingredient> = entity.map { it.toDomain() }

            override suspend fun createCall(): ResultKt<List<IngredientResult>> = api.getIngredients(searchQuery)
        }.asFlow()

    suspend fun getRecipes(category: Category): ResultKt<List<Recipe>> = api.getRecipesByCategory(category.id)

    suspend fun getRecipe(recipeId: RecipeId): ResultKt<Recipe> = api.getRecipe(recipeId)

    suspend fun sendReview(review: String): ResultKt<Review> = api.sendReview(review)

    suspend fun addIngredientToShoppingList(ingredient: Ingredient): ResultKt<Unit> = api.addIngredientToShoppingList(ingredient)

    suspend fun addFavorite(recipeId: RecipeId): ResultKt<Unit> = api.addFavorite(recipeId)

    suspend fun removeFavorite(recipeId: RecipeId): ResultKt<Unit> = api.removeFavorite(recipeId)

    suspend fun searchRecipesByIngredients(searchedIngredients: List<Ingredient>, orderBy: RecipesOrderBy) = api.searchRecipesByIngredients(searchedIngredients.map { it.id }, orderBy)
}