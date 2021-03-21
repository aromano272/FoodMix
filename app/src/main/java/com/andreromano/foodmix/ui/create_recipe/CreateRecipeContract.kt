package com.andreromano.foodmix.ui.create_recipe

import android.net.Uri
import androidx.lifecycle.LiveData
import com.andreromano.foodmix.core.ErrorKt
import com.andreromano.foodmix.core.Event
import com.andreromano.foodmix.core.Minutes
import com.andreromano.foodmix.domain.model.Direction
import com.andreromano.foodmix.domain.model.Ingredient

interface CreateRecipeContract {

    interface ViewModel : ViewState, ViewActions

    interface ViewState {
        val navigation: LiveData<Event<ViewInstruction>>
        val error: LiveData<Event<ErrorKt>>

        val availableIngredientsToPickFrom: LiveData<List<Ingredient>>

        val nameInput: LiveData<String>
        val attachedImage: LiveData<Uri?>
        val cookingTime: LiveData<Minutes>
        val servingsCount: LiveData<Int>
        val calories: LiveData<Int>

        val ingredients: LiveData<List<Ingredient>>
        val newIngredientInput: LiveData<String>
        val isNewIngredientInputValid: LiveData<Boolean>

        val directions: LiveData<List<Direction>>
        val newDirectionTitle: LiveData<String>
        val newDirectionDescription: LiveData<String>
        val newDirectionAttachedImage: LiveData<Uri?>

        val isCreateRecipeLoading: LiveData<Boolean>
    }

    interface ViewActions {
        fun newIngredientInputChanged(input: String)
        fun addIngredientClicked()

        fun newDirectionTitleInputChanged(title: String)
        fun newDirectionDescriptionInputChanged(description: String)
        fun newDirectionPhotoClicked()
        fun newDirectionPhotoAttachSuccess(uri: Uri)
        fun newDirectionPhotoAttachFailure(error: ErrorKt)
        fun newDirectionRemovePhotoClicked()
        fun addDirectionClicked()

        fun backClicked()
        fun backConfirmed()
        fun createRecipeClicked()
    }

    sealed class ViewInstruction {
        object OpenPhotoPicker : ViewInstruction()
        object OpenCamera : ViewInstruction()
        object OpenGallery : ViewInstruction()
        object ShowBackConfirmation : ViewInstruction()
        object NavigateBack : ViewInstruction()
    }

}