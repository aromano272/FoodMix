package com.andreromano.foodmix.ui.recipe_details

import androidx.lifecycle.*
import com.andreromano.foodmix.core.*
import com.andreromano.foodmix.data.Repository
import com.andreromano.foodmix.domain.model.*
import com.andreromano.foodmix.extensions.launch
import com.andreromano.foodmix.extensions.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(
    private val initialRecipe: Recipe,
    private val repository: Repository
) : ViewModel(), RecipeDetailsContract.ViewModel {

    private val _navigation = MutableLiveData<RecipeDetailsContract.ViewInstruction>()
    override val navigation: LiveData<Event<RecipeDetailsContract.ViewInstruction>> = _navigation.map { Event(it) }

    private val _error = MutableLiveData<ErrorKt>()
    override val error: LiveData<Event<ErrorKt>> = _error.map { Event(it) }

    private val _showAddIngredientToShoppingListSuccess = MutableLiveData<Ingredient>()
    override val showAddIngredientToShoppingListSuccess: LiveData<Event<Ingredient>> = _showAddIngredientToShoppingListSuccess.map { Event(it) }

    private val _ingredientsToBeAddedToShoppingListLoading = MutableLiveData<List<Ingredient>>()
    override val ingredientsToBeAddedToShoppingListLoading: LiveData<List<Ingredient>> = _ingredientsToBeAddedToShoppingListLoading

    private val _isFavorite = MutableLiveData<Boolean>(initialRecipe.isFavorite)
    override val isFavorite: LiveData<Boolean> = _isFavorite

    private val _isFavoriteLoading = MutableLiveData<Boolean>(false)
    override val isFavoriteLoading: LiveData<Boolean> = _isFavoriteLoading

    private val _imageUrl = MutableLiveData<String>(initialRecipe.imageUrl)
    override val imageUrl: LiveData<String> = _imageUrl

    private val _title = MutableLiveData<String>(initialRecipe.title)
    override val title: LiveData<String> = _title

    private val _description = MutableLiveData<String>(initialRecipe.description)
    override val description: LiveData<String> = _description

    private val _rating = MutableLiveData<Double>(initialRecipe.rating)
    override val rating: LiveData<Double> = _rating

    private val _reviewsCount = MutableLiveData<Int>(initialRecipe.reviews.size)
    override val reviewsCount: LiveData<Int> = _reviewsCount

    private val _duration = MutableLiveData<Minutes>(initialRecipe.duration)
    override val duration: LiveData<Minutes> = _duration

    private val _calories = MutableLiveData<Int>(initialRecipe.calories)
    override val calories: LiveData<Int> = _calories

    private val _servings = MutableLiveData<Int>(initialRecipe.servings)
    override val servings: LiveData<Int> = _servings

    private val _categories = MutableLiveData<List<Category>>(initialRecipe.categories)
    override val categories: LiveData<List<Category>> = _categories

    private val _ingredients = MutableLiveData<List<Ingredient>>(initialRecipe.ingredients)
    override val ingredients: LiveData<List<Ingredient>> = _ingredients

    private val _directions = MutableLiveData<List<Direction>>(initialRecipe.directions)
    override val directions: LiveData<List<Direction>> = _directions

    private val _reviews = MutableLiveData<List<Review>>(initialRecipe.reviews)
    override val reviews: LiveData<List<Review>> = _reviews

    private val _reviewInput = MutableLiveData<String>()
    override val reviewInput: LiveData<String> = _reviewInput

    private val _reviewButtonState = MutableLiveData<RecipeDetailsContract.ViewState.ButtonState>()
    override val reviewButtonState: LiveData<RecipeDetailsContract.ViewState.ButtonState> = _reviewButtonState

    private val _selectedTab = MutableLiveData<RecipeDetailsContract.ViewState.Tab>(RecipeDetailsContract.ViewState.Tab.INGREDIENTS)
    override val selectedTab: LiveData<RecipeDetailsContract.ViewState.Tab> = _selectedTab

    init {
        viewModelScope.launch {
            val result = repository.getRecipe(initialRecipe.id)

            when (result) {
                is ResultKt.Success -> {
                    _isFavorite.value = result.data.isFavorite
                    _imageUrl.value = result.data.imageUrl
                    _title.value = result.data.title
                    _description.value = result.data.description
                    _rating.value = result.data.rating
                    _reviewsCount.value = result.data.reviews.size
                    _duration.value = result.data.duration
                    _calories.value = result.data.calories
                    _servings.value = result.data.servings
                    _categories.value = result.data.categories
                    _ingredients.value = result.data.ingredients
                    _directions.value = result.data.directions
                    _reviews.value = result.data.reviews
                }
                is ResultKt.Failure -> _error.value = result.error
            }
        }
    }

    override fun backClicked() {
        _navigation.value = RecipeDetailsContract.ViewInstruction.NavigateBack
    }

    override fun favoriteClicked() = launch {
        if (_isFavoriteLoading.value == true) return@launch

        _isFavoriteLoading.value = true
        val newFavoriteState = isFavorite.value != true

        val result =
            if (newFavoriteState) repository.addFavorite(initialRecipe.id)
            else repository.removeFavorite(initialRecipe.id)

        when (result) {
            is ResultKt.Success -> _isFavorite.value = newFavoriteState
            is ResultKt.Failure -> _error.value = result.error
        }

        _isFavoriteLoading.value = false
    }

    override fun tabSelected(tab: RecipeDetailsContract.ViewState.Tab) {
        _selectedTab.value = tab
    }

    override fun addIngredientToShoppingList(ingredient: Ingredient) = launch {
        if (_ingredientsToBeAddedToShoppingListLoading.value?.contains(ingredient) == true) return@launch

        _ingredientsToBeAddedToShoppingListLoading.value = _ingredientsToBeAddedToShoppingListLoading.value?.plus(ingredient)
        val result = repository.addIngredientToShoppingList(ingredient)

        when (result) {
            is ResultKt.Success -> _showAddIngredientToShoppingListSuccess.value = ingredient
            is ResultKt.Failure -> _error.value = result.error
        }

        _ingredientsToBeAddedToShoppingListLoading.value = _ingredientsToBeAddedToShoppingListLoading.value?.minus(ingredient)
    }

    override fun reviewInputChanged(comment: String) {
        if (_reviewButtonState.value == RecipeDetailsContract.ViewState.ButtonState.LOADING) return
        _reviewButtonState.value =
            if (comment.isBlank()) RecipeDetailsContract.ViewState.ButtonState.DISABLED
            else RecipeDetailsContract.ViewState.ButtonState.ENABLED
        _reviewInput.value = comment
    }

    override fun sendReviewClicked() = launch {
        val input = _reviewInput.value
        if (input.isNullOrBlank() || _reviewButtonState.value == RecipeDetailsContract.ViewState.ButtonState.LOADING) return@launch

        _reviewButtonState.value = RecipeDetailsContract.ViewState.ButtonState.LOADING
        val result = repository.sendReview(input)

        when (result) {
            is ResultKt.Success -> _reviews.value = _reviews.value?.plus(result.data)
            is ResultKt.Failure -> _error.value = result.error
        }

        _reviewButtonState.value = RecipeDetailsContract.ViewState.ButtonState.DISABLED
        _reviewInput.value = ""
    }

    override fun reviewUserClicked(review: Review) {
        _navigation.value = RecipeDetailsContract.ViewInstruction.NavigateToUserDetails(review.user)
    }

    override fun reviewFavoriteClicked(review: Review) {
        // TODO
    }

    override fun reviewReplyClicked(review: Review) {
        // TODO
    }


    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val recipe: Recipe,
        private val repository: Repository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RecipeDetailsViewModel(
                recipe,
                repository
            ) as T
        }
    }
}