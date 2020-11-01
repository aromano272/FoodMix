package com.andreromano.foodmix.ui.recipe_details

import androidx.lifecycle.LiveData
import com.andreromano.foodmix.core.ErrorKt
import com.andreromano.foodmix.core.Event
import com.andreromano.foodmix.core.Ingredient
import com.andreromano.foodmix.core.Minutes
import com.andreromano.foodmix.domain.model.Category
import com.andreromano.foodmix.domain.model.Direction
import com.andreromano.foodmix.domain.model.Review
import com.andreromano.foodmix.ui.model.ListState

interface RecipeDetailsContract {

    interface ViewModel : ViewState, ViewActions

    interface ViewState {
        val navigation: LiveData<Event<ViewInstruction>>
        val error: LiveData<Event<ErrorKt>>

        val showAddIngredientToShoppingListSuccess: LiveData<Event<Ingredient>>
        val ingredientsToBeAddedToShoppingListLoading: LiveData<List<Ingredient>>

        val isFavorite: LiveData<Boolean>
        val isFavoriteLoading: LiveData<Boolean>
        val imageUrl: LiveData<String>
        val title: LiveData<String>
        val description: LiveData<String>
        val rating: LiveData<Double>
        val reviewsCount: LiveData<Int>
        val duration: LiveData<Minutes>
        val calories: LiveData<Int>
        val servings: LiveData<Int>
        val categories: LiveData<List<Category>>
        val ingredients: LiveData<List<Ingredient>>
        val directions: LiveData<List<Direction>>
        val reviews: LiveData<List<Review>>

        val reviewInput: LiveData<String>
        val reviewButtonState: LiveData<ButtonState>

        val selectedTab: LiveData<Tab>

        enum class ButtonState {
            ENABLED,
            DISABLED,
            LOADING
        }

        enum class Tab {
            INGREDIENTS,
            DIRECTIONS,
            REVIEWS
        }
    }

    interface ViewActions {
        fun backClicked()
        fun tabSelected(tab: ViewState.Tab)

        fun addIngredientToShoppingList(ingredient: Ingredient)
        fun reviewInputChanged(comment: String)
        fun sendReviewClicked()
    }

    sealed class ViewInstruction {
        object NavigateBack : ViewInstruction()
    }

}