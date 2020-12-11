package com.andreromano.foodmix.ui.category_recipes

import androidx.lifecycle.*
import com.andreromano.foodmix.core.Event
import com.andreromano.foodmix.core.ResultKt
import com.andreromano.foodmix.data.Repository
import com.andreromano.foodmix.domain.model.Category
import com.andreromano.foodmix.domain.model.Recipe
import com.andreromano.foodmix.extensions.map
import com.andreromano.foodmix.ui.model.ListState
import kotlinx.coroutines.launch

class CategoryRecipesViewModel(
    private val category: Category,
    private val repository: Repository
) : ViewModel(), CategoryRecipesContract.ViewModel {

    private val _navigation = MutableLiveData<CategoryRecipesContract.ViewInstruction>()
    override val navigation: LiveData<Event<CategoryRecipesContract.ViewInstruction>> = _navigation.map { Event(it) }

    private val _recipes = MutableLiveData<ListState<Recipe>>(ListState.Loading(null))
    override val recipes: LiveData<ListState<Recipe>> = _recipes

    init {
        viewModelScope.launch {
            val result = repository.getRecipes(category)

            val newValue = when (result) {
                is ResultKt.Success ->  if (result.data.isEmpty()) ListState.EmptyState else ListState.Results(result.data)
                is ResultKt.Failure -> ListState.Error(result.error)
            }

            _recipes.value = newValue
        }
    }

    override fun recipeClicked(recipe: Recipe) {
        _navigation.value = CategoryRecipesContract.ViewInstruction.NavigateToRecipeDetails(recipe)
    }



    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val category: Category,
        private val repository: Repository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CategoryRecipesViewModel(
                category,
                repository
            ) as T
        }
    }
}