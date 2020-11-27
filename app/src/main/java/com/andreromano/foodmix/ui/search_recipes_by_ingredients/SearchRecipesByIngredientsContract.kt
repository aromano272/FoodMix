package com.andreromano.foodmix.ui.search_recipes_by_ingredients

import androidx.lifecycle.LiveData
import com.andreromano.foodmix.core.Event
import com.andreromano.foodmix.domain.model.Recipe
import com.andreromano.foodmix.domain.model.RecipesOrderBy
import com.andreromano.foodmix.ui.model.ListState

interface SearchRecipesByIngredientsContract {

    interface ViewModel : ViewState, ViewActions

    interface ViewState {
        val navigation: LiveData<Event<ViewInstruction>>

        val orderBy: LiveData<RecipesOrderBy>
        val searchDetailsInfo: LiveData<SearchDetailsInfo?>
        val results: LiveData<ListState<Recipe>>

        data class SearchDetailsInfo(
            val total: Int,
            val searchQueries: List<String>
        )
    }

    interface ViewActions {
        fun backClicked()
        fun orderByChanged(orderBy: RecipesOrderBy)
        fun recipeClicked(recipe: Recipe)
    }

    sealed class ViewInstruction {
        data class NavigateToRecipeDetails(val recipe: Recipe) : ViewInstruction()
        object NavigateBack : ViewInstruction()
    }

}