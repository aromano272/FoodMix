package com.andreromano.foodmix.ui.category_recipes

import androidx.lifecycle.*
import com.andreromano.foodmix.core.Event
import com.andreromano.foodmix.core.ResultFm
import com.andreromano.foodmix.data.RecipeRepository
import com.andreromano.foodmix.domain.model.Category
import com.andreromano.foodmix.domain.model.Recipe
import com.andreromano.foodmix.ui.model.ListState
import kotlinx.coroutines.launch

class CategoryRecipesViewModel(
    private val category: Category,
    private val recipeRepository: RecipeRepository
) : ViewModel(), CategoryRecipesContract.ViewModel {

    private val _navigation = MutableLiveData<Event<CategoryRecipesContract.ViewInstruction>>()
    override val navigation: LiveData<Event<CategoryRecipesContract.ViewInstruction>> = _navigation

    private val _recipes = MutableLiveData<ListState<Recipe>>(ListState.Loading)
    override val recipes: LiveData<ListState<Recipe>> = _recipes

    init {
        viewModelScope.launch {
            val result = recipeRepository.getRecipes(category)

            val newValue = when (result) {
                is ResultFm.Success ->  if (result.data.isEmpty()) ListState.EmptyState else ListState.Results(result.data)
                is ResultFm.Failure -> ListState.Error(result.error)
            }

            _recipes.value = newValue
        }
    }

    override fun recipeClicked(recipe: Recipe) {
        _navigation.value = Event(CategoryRecipesContract.ViewInstruction.NavigateToRecipeDetails(recipe))
    }


    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val category: Category,
        private val recipeRepository: RecipeRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CategoryRecipesViewModel(
                category,
                recipeRepository
            ) as T
        }
    }
}