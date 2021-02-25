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
import kotlinx.coroutines.flow.*

class CreateRecipeViewModel(
    private val repository: Repository
) : ViewModel(), CreateRecipeContract.ViewModel {

    private val _navigation = MutableSharedFlow<CreateRecipeContract.ViewInstruction>()
    override val navigation: LiveData<Event<CreateRecipeContract.ViewInstruction>> = _navigation.mapLatest { Event(it) }.asLiveData()

    private val _error = MutableSharedFlow<ErrorKt>()
    override val error: LiveData<Event<ErrorKt>> = _error.mapLatest { Event(it) }.asLiveData()

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

    private val _ingredients = MutableStateFlow<List<String>>(emptyList())
    override val ingredients: LiveData<List<String>> = _ingredients.asLiveData()

    private val _newIngredientInput = MutableStateFlow<String>("")
    override val newIngredientInput: LiveData<String> = _newIngredientInput.asLiveData()

    private val _directions = MutableStateFlow<List<Direction>>(emptyList())
    override val directions: LiveData<List<Direction>> = _directions.asLiveData()

    private val _newDirectionsInput = MutableStateFlow<Direction?>(null)
    override val newDirectionsInput: LiveData<Direction?> = _newDirectionsInput.asLiveData()

    private val createRecipeAction = MutableSharedFlow<CreateRecipeRequest>()
    private val createRecipeResult = createRecipeAction.transformLatest {
        emit(Resource.Loading(null))
        emit(repository.createRecipe(it))
    }

    override val isCreateRecipeLoading: LiveData<Boolean> = TODO()


    override fun addIngredientClicked() {
        TODO("Not yet implemented")
    }

    override fun addDirectionPhotoClicked() {
        TODO("Not yet implemented")
    }

    override fun addDirectionPhotoAttachSuccess(uri: Uri) {
        TODO("Not yet implemented")
    }

    override fun addDirectionPhotoAttachFailure(error: ErrorKt) {
        TODO("Not yet implemented")
    }

    override fun addDirectionRemovePhotoClicked() {
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
        private val repository: Repository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CreateRecipeViewModel(
                repository
            ) as T
        }
    }

}