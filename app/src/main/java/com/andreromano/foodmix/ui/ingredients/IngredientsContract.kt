package com.andreromano.foodmix.ui.ingredients

import androidx.lifecycle.LiveData
import com.andreromano.foodmix.core.ErrorKt
import com.andreromano.foodmix.core.Event
import com.andreromano.foodmix.domain.model.Ingredient
import com.andreromano.foodmix.domain.model.IngredientType
import com.andreromano.foodmix.ui.model.ListState

interface IngredientsContract {

    interface ViewModel : ViewState, ViewActions

    interface ViewState {
        val navigation: LiveData<Event<ViewInstruction>>
        val error: LiveData<Event<ErrorKt>>

        val searchQueryInput: LiveData<String>
        val selectedIngredientType: LiveData<IngredientType?>
        val ingredients: LiveData<ListState<Ingredient>>
        val selectedIngredients: LiveData<List<Ingredient>>
    }

    interface ViewActions {
        fun ingredientTypeClicked(ingredientType: IngredientType?)
        fun searchQueryInputChanged(query: String)
        fun findRecipesClicked()

        fun ingredientClicked(ingredient: Ingredient)
        fun removeSelectedIngredientClicked(ingredient: Ingredient)
    }

    sealed class ViewInstruction {
        data class NavigateToSearchRecipesByIngredients(val ingredients: List<Ingredient>) : ViewInstruction()
    }

}