package com.andreromano.foodmix.ui.ingredients

import androidx.lifecycle.*
import com.andreromano.foodmix.core.*
import com.andreromano.foodmix.data.Repository
import com.andreromano.foodmix.domain.model.Ingredient
import com.andreromano.foodmix.domain.model.IngredientType
import com.andreromano.foodmix.extensions.launch
import com.andreromano.foodmix.ui.mapper.toListState
import com.andreromano.foodmix.ui.model.ListState
import kotlinx.coroutines.flow.*
import timber.log.Timber

class IngredientsViewModel(
    private val repository: Repository
) : ViewModel(), IngredientsContract.ViewModel {

    private val _navigation: MutableSharedFlow<IngredientsContract.ViewInstruction> = MutableSharedFlow()
    override val navigation: LiveData<Event<IngredientsContract.ViewInstruction>> = _navigation.map { Event(it) }.asLiveData()

    private val _searchQueryInput = MutableStateFlow("")
    override val searchQueryInput: LiveData<String> = _searchQueryInput.asLiveData()

    private val _selectedIngredientType = MutableStateFlow<IngredientType?>(null)
    override val selectedIngredientType: LiveData<IngredientType?> = _selectedIngredientType.asLiveData()

    private val ingredientsResult: SharedFlow<Resource<List<Ingredient>>> =
        _searchQueryInput
            .debounce(300)
            .combine(_selectedIngredientType) { query, type -> query to type }
            .flatMapLatest { (query, type) ->
                Timber.e("miesmo flatMapLatest")
                repository.getIngredients(query, type)
            }
            .shareIn(viewModelScope, SharingStarted.Lazily)


    override val ingredients: LiveData<ListState<Ingredient>> = ingredientsResult.mapLatest { it.toListState() }.asLiveData()

    override val error: LiveData<Event<ErrorKt>> =
        ingredientsResult
            .filterIsInstance<Resource.Failure<*>>()
            .mapLatest { Event(it.error) }
            .asLiveData()

    private val _selectedIngredients = MutableStateFlow(emptyList<Ingredient>())
    override val selectedIngredients: LiveData<List<Ingredient>> = _selectedIngredients.asLiveData()

    override fun ingredientTypeClicked(ingredientType: IngredientType?) {
        _selectedIngredientType.value = if (_selectedIngredientType.value != ingredientType) ingredientType else null
    }

    override fun searchQueryInputChanged(query: String) {
        _searchQueryInput.value = query
    }

    override fun findRecipesClicked() = launch {
        _navigation.emit(IngredientsContract.ViewInstruction.NavigateToSearchRecipesByIngredients(_selectedIngredients.value))
    }

    override fun ingredientClicked(ingredient: Ingredient) {
        val oldSelectedIngredients = _selectedIngredients.value
        val newSelectedIngredients =
            if (oldSelectedIngredients.contains(ingredient)) oldSelectedIngredients - ingredient
            else oldSelectedIngredients + ingredient

        _selectedIngredients.value = newSelectedIngredients
    }

    override fun removeSelectedIngredientClicked(ingredient: Ingredient) {
        _selectedIngredients.value = _selectedIngredients.value - ingredient
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val repository: Repository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return IngredientsViewModel(
                repository
            ) as T
        }
    }

}