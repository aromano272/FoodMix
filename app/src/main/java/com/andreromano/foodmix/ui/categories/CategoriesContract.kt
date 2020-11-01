package com.andreromano.foodmix.ui.categories

import androidx.lifecycle.LiveData
import com.andreromano.foodmix.core.Event
import com.andreromano.foodmix.domain.model.Category
import com.andreromano.foodmix.ui.model.ListState

interface CategoriesContract {

    interface ViewModel : ViewState, ViewActions

    interface ViewState {
        val navigation: LiveData<Event<ViewInstruction>>
        val searchQueryInput: LiveData<String>
        val categories: LiveData<ListState<Category>>
    }

    interface ViewActions {
        fun searchQueryInputChanged(query: String)
        fun categoryClicked(category: Category)
    }

    sealed class ViewInstruction {
        data class NavigateToCategoryRecipes(val category: Category) : ViewInstruction()
    }

}