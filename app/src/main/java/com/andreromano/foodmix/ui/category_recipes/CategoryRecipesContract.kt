package com.andreromano.foodmix.ui.category_recipes

import androidx.lifecycle.LiveData
import com.andreromano.foodmix.core.Event
import com.andreromano.foodmix.domain.model.Recipe
import com.andreromano.foodmix.ui.model.ListState

interface CategoryRecipesContract {

    interface ViewModel : ViewState, ViewActions

    interface ViewState {
        val navigation: LiveData<Event<ViewInstruction>>

        val recipes: LiveData<ListState<Recipe>>
    }

    interface ViewActions {
        fun recipeClicked(recipe: Recipe)
    }

    sealed class ViewInstruction {
        data class NavigateToRecipeDetails(val recipe: Recipe) : ViewInstruction()
    }

}