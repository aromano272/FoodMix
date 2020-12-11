package com.andreromano.foodmix.ui.search_recipes_by_ingredients

import androidx.lifecycle.*
import com.andreromano.foodmix.core.Event
import com.andreromano.foodmix.core.Resource
import com.andreromano.foodmix.core.ResultKt
import com.andreromano.foodmix.data.Repository
import com.andreromano.foodmix.domain.model.Ingredient
import com.andreromano.foodmix.domain.model.Recipe
import com.andreromano.foodmix.domain.model.RecipesOrderBy
import com.andreromano.foodmix.extensions.launch
import com.andreromano.foodmix.extensions.shareHere
import com.andreromano.foodmix.ui.mapper.toListState
import com.andreromano.foodmix.ui.model.ListState
import kotlinx.coroutines.flow.*

class SearchRecipesByIngredientsViewModel(
    private val searchedIngredients: List<Ingredient>,
    private val repository: Repository
) : ViewModel(), SearchRecipesByIngredientsContract.ViewModel {

    private val _navigation: MutableSharedFlow<SearchRecipesByIngredientsContract.ViewInstruction> = MutableSharedFlow()
    override val navigation: LiveData<Event<SearchRecipesByIngredientsContract.ViewInstruction>> = _navigation.map { Event(it) }.asLiveData()

    private val _orderBy: MutableStateFlow<RecipesOrderBy> = MutableStateFlow(RecipesOrderBy.RELEVANCE)
    override val orderBy: LiveData<RecipesOrderBy> = _orderBy.asLiveData()

    private val searchResult: SharedFlow<Resource<List<Recipe>>> = _orderBy.transformLatest { orderBy ->
        emit(Resource.Loading(null))
        emit(repository.searchRecipesByIngredients(searchedIngredients, orderBy).toResource())
    }.shareHere(this)

    override val searchDetailsInfo: LiveData<SearchRecipesByIngredientsContract.ViewState.SearchDetailsInfo?> =
        searchResult
            .mapLatest {
                if (it is Resource.Success) SearchRecipesByIngredientsContract.ViewState.SearchDetailsInfo(it.data.size, searchedIngredients.map { it.name })
                else null
            }
            .asLiveData()

    override val results: LiveData<ListState<Recipe>> = searchResult.mapLatest { it.toListState() }.asLiveData()


    override fun backClicked() = launch {
        _navigation.emit(SearchRecipesByIngredientsContract.ViewInstruction.NavigateBack)
    }

    override fun orderByChanged(orderBy: RecipesOrderBy) {
        _orderBy.value = orderBy
    }

    override fun recipeClicked(recipe: Recipe) = launch {
        _navigation.emit(SearchRecipesByIngredientsContract.ViewInstruction.NavigateToRecipeDetails(recipe))
    }


    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val searchedIngredients: List<Ingredient>,
        private val repository: Repository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SearchRecipesByIngredientsViewModel(
                searchedIngredients,
                repository
            ) as T
        }
    }

}