package com.andreromano.foodmix.ui.account

import androidx.lifecycle.LiveData
import com.andreromano.foodmix.core.ErrorKt
import com.andreromano.foodmix.core.Event

interface AccountContract {

    interface ViewModel : ViewState, ViewActions

    interface ViewState {
        val navigation: LiveData<Event<ViewInstructions>>

        val backgroundUrl: LiveData<String>
        val avatarUrl: LiveData<String>
        val username: LiveData<String>
        val description: LiveData<String>
        val totalRecipesCount: LiveData<Int>
        val totalCookbooksCount: LiveData<Int>
        val myRecipesCount: LiveData<Int>
        val myCookbooksCount: LiveData<Int>
        val shoppingListCount: LiveData<Int>
    }

    interface ViewActions {
        fun myRecipesClicked()
        fun myCookbooksClicked()
        fun cookbooksClicked()
        fun recipesClicked()
        fun shoppingListClicked()
        fun addRecipeClicked()
        fun createCookbookClicked()
    }

    sealed class ViewInstructions {
        object NavigateToMyRecipes : ViewInstructions()
        object NavigateToMyCookbooks : ViewInstructions()
        object NavigateToSavedRecipes : ViewInstructions()
        object NavigateToSavedCookbooks : ViewInstructions()
        object NavigateToShoppingList : ViewInstructions()
        object NavigateToAddRecipe : ViewInstructions()
        object NavigateToCreateCookbook : ViewInstructions()
    }

}