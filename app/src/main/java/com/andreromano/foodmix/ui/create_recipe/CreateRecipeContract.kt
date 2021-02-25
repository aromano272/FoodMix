package com.andreromano.foodmix.ui.create_recipe

import android.net.Uri
import androidx.lifecycle.LiveData
import com.andreromano.foodmix.core.ErrorKt
import com.andreromano.foodmix.core.Event
import com.andreromano.foodmix.core.Minutes
import com.andreromano.foodmix.domain.model.Direction

interface CreateRecipeContract {

    interface ViewModel : ViewState, ViewActions

    interface ViewState {
        val navigation: LiveData<Event<ViewInstruction>>
        val error: LiveData<Event<ErrorKt>>

        val nameInput: LiveData<String>
        val attachedImage: LiveData<Uri?>
        val cookingTime: LiveData<Minutes>
        val servingsCount: LiveData<Int>
        val calories: LiveData<Int>

        val ingredients: LiveData<List<String>>
        val newIngredientInput: LiveData<String>

        val directions: LiveData<List<Direction>>
        val newDirectionsInput: LiveData<Direction?>

        val isCreateRecipeLoading: LiveData<Boolean>
    }

    interface ViewActions {
        fun addIngredientClicked()
        fun addDirectionPhotoClicked()
        fun addDirectionPhotoAttachSuccess(uri: Uri)
        fun addDirectionPhotoAttachFailure(error: ErrorKt)
        fun addDirectionRemovePhotoClicked()
        fun addDirectionClicked()
        fun backClicked()
        fun backConfirmed()
        fun createRecipeClicked()
    }

    sealed class ViewInstruction {
        object OpenPhotoPicker : ViewInstruction()
        object ShowBackConfirmation : ViewInstruction()
        object NavigateBack : ViewInstruction()
    }

}