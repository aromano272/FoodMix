package com.andreromano.foodmix.ui.recipe_details

import com.airbnb.epoxy.EpoxyAsyncUtil
import com.airbnb.epoxy.EpoxyController
import com.andreromano.foodmix.core.Ingredient
import com.andreromano.foodmix.domain.model.Direction
import com.andreromano.foodmix.domain.model.Review
import com.andreromano.foodmix.ui.utils.EpoxyModelProperty

class RecipeDetailsPagerController(
    private val addIngredientClicked: (Ingredient) -> Unit
) : EpoxyController(
    EpoxyAsyncUtil.getAsyncBackgroundHandler(),
    EpoxyAsyncUtil.getAsyncBackgroundHandler()
) {

    var tab: RecipeDetailsContract.ViewState.Tab by EpoxyModelProperty { RecipeDetailsContract.ViewState.Tab.INGREDIENTS }

    var ingredients: List<Ingredient> by EpoxyModelProperty { emptyList<Ingredient>() }
    var directions: List<Direction> by EpoxyModelProperty { emptyList<Direction>() }
    var reviews: List<Review> by EpoxyModelProperty { emptyList<Review>() }

    override fun buildModels() {
        when (tab) {
            RecipeDetailsContract.ViewState.Tab.INGREDIENTS -> ingredients.forEachIndexed { index, ingredient ->
                recipeIngredient {
                    id(index)
                    ingredient(ingredient)
                    onAddClick(addIngredientClicked)
                }
            }
            RecipeDetailsContract.ViewState.Tab.DIRECTIONS -> TODO()
            RecipeDetailsContract.ViewState.Tab.REVIEWS -> TODO()
        }
    }
}