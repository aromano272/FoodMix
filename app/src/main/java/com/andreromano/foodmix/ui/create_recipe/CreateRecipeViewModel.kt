package com.andreromano.foodmix.ui.create_recipe

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.andreromano.foodmix.core.ErrorKt
import com.andreromano.foodmix.core.Event
import com.andreromano.foodmix.core.Minutes
import com.andreromano.foodmix.core.Resource
import com.andreromano.foodmix.data.Repository
import com.andreromano.foodmix.domain.model.CreateRecipeRequest
import com.andreromano.foodmix.domain.model.Direction
import com.andreromano.foodmix.domain.model.Ingredient
import com.andreromano.foodmix.extensions.filterResourceFailure
import com.andreromano.foodmix.extensions.launch
import com.andreromano.foodmix.extensions.shareHere
import com.andreromano.foodmix.extensions.withLatestFrom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class CreateRecipeViewModel(
    private val repository: Repository,
) : ViewModel(), CreateRecipeContract.ViewModel {

    private val _navigation = MutableSharedFlow<CreateRecipeContract.ViewInstruction>()
    override val navigation: LiveData<Event<CreateRecipeContract.ViewInstruction>> = _navigation.mapLatest { Event(it) }.asLiveData()

    private val availableIngredientsResult = repository.getIngredients().shareHere(this)
    private val availableIngredientsData = availableIngredientsResult.mapNotNull { it.data }
    override val availableIngredientsToPickFrom: LiveData<List<Ingredient>> = availableIngredientsData.asLiveData()

    private val _nameInput = MutableStateFlow("")
    override val nameInput: LiveData<String> = _nameInput.asLiveData()

    private val _attachedImage = MutableStateFlow<Uri?>(null)
    override val attachedImage: LiveData<Uri?> = _attachedImage.asLiveData()

    private val _cookingTime = MutableStateFlow<Minutes>(15)
    override val cookingTime: LiveData<Minutes> = _cookingTime.asLiveData()

    private val _servingsCount = MutableStateFlow<Int>(3)
    override val servingsCount: LiveData<Int> = _servingsCount.asLiveData()

    private val _calories = MutableStateFlow<Int>(200)
    override val calories: LiveData<Int> = _calories.asLiveData()

    private val _ingredients = MutableStateFlow<List<Ingredient>>(emptyList())
    override val ingredients: LiveData<List<Ingredient>> = _ingredients.asLiveData()

    private val _newIngredientInput = MutableStateFlow<String>("")
    override val newIngredientInput: LiveData<String> = _newIngredientInput.asLiveData()

    private val newIngredientInputIngredient = _newIngredientInput.withLatestFrom(availableIngredientsData) { input, availableIngredients ->
        availableIngredients.find { it.name.equals(input, ignoreCase = true) }
    }.flowOn(Dispatchers.IO).shareHere(this)

    override val isNewIngredientInputValid: LiveData<Boolean> = newIngredientInputIngredient.mapLatest { it != null }.asLiveData()

    private val _directions = MutableStateFlow<List<Direction>>(emptyList())
    override val directions: LiveData<List<Direction>> = _directions.asLiveData()

    private val _newDirectionTitle = MutableStateFlow<String>("")
    override val newDirectionTitle: LiveData<String> = _newDirectionTitle.asLiveData()

    private val _newDirectionDescription = MutableStateFlow<String>("")
    override val newDirectionDescription: LiveData<String> = _newDirectionDescription.asLiveData()

    private val _newDirectionAttachedImage = MutableStateFlow<Uri?>(null)
    override val newDirectionAttachedImage: LiveData<Uri?> = _newDirectionAttachedImage.asLiveData()

    private val createRecipeAction = MutableSharedFlow<CreateRecipeRequest>()
    private val createRecipeResult = createRecipeAction.transformLatest {
        emit(Resource.Loading(null))
        emit(repository.createRecipe(it).toResource())
    }

    override val isCreateRecipeLoading: LiveData<Boolean> = createRecipeResult.mapLatest { it is Resource.Loading }.asLiveData()

    private val _error = MutableSharedFlow<ErrorKt>()
    override val error: LiveData<Event<ErrorKt>> =
        merge(
            _error,
            availableIngredientsResult.filterResourceFailure().map { it.error },
            createRecipeResult.filterResourceFailure().map { it.error }
        ).mapLatest { Event(it) }
            .asLiveData()

    override fun newIngredientInputChanged(input: String) {
        _newIngredientInput.value = input
    }

    override fun addIngredientClicked() {
        val ingredient = newIngredientInputIngredient.replayCache.firstOrNull() ?: return
        _ingredients.value += ingredient
        _newIngredientInput.value = ""
    }

    override fun newDirectionTitleInputChanged(title: String) {
        _newDirectionTitle.value = title
    }

    override fun newDirectionDescriptionInputChanged(description: String) {
        _newDirectionDescription.value = description
    }

    override fun newDirectionPhotoClicked() = launch {
        _navigation.emit(CreateRecipeContract.ViewInstruction.OpenPhotoPicker)
    }

    override fun newDirectionPhotoAttachSuccess(uri: Uri) {
        TODO("Not yet implemented")
    }

    override fun newDirectionPhotoAttachFailure(error: ErrorKt) {
        TODO("Not yet implemented")
    }

    override fun newDirectionRemovePhotoClicked() {
        TODO("Not yet implemented")
    }

    override fun addDirectionClicked() {
        TODO("Not yet implemented")
    }

    override fun backClicked() {
        TODO("Not yet implemented")
    }

    override fun backConfirmed() {
        TODO("Not yet implemented")
    }

    override fun createRecipeClicked() {
        TODO("Not yet implemented")
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val repository: Repository,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CreateRecipeViewModel(
                repository
            ) as T
        }
    }
}