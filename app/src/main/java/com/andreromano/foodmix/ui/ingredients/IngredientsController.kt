package com.andreromano.foodmix.ui.ingredients

import com.airbnb.epoxy.EpoxyAsyncUtil
import com.airbnb.epoxy.EpoxyController
import com.andreromano.foodmix.core.IngredientId
import com.andreromano.foodmix.domain.model.Ingredient
import com.andreromano.foodmix.ui.epoxy_models.epoxyTextView
import com.andreromano.foodmix.ui.epoxy_models.ingredient
import com.andreromano.foodmix.ui.mapper.errorMessage
import com.andreromano.foodmix.ui.model.ListState
import com.andreromano.foodmix.ui.utils.EpoxyModelProperty

class IngredientsController(
    private val ingredientClicked: (Ingredient) -> Unit
) : EpoxyController(
    EpoxyAsyncUtil.getAsyncBackgroundHandler(),
    EpoxyAsyncUtil.getAsyncBackgroundHandler()
) {

    var listState: ListState<Ingredient> by EpoxyModelProperty { ListState.Loading }
    var selectedIngredientIds: List<IngredientId> by EpoxyModelProperty { emptyList() }

    override fun buildModels() {
        val listState = listState

        when (listState) {
            ListState.Loading -> epoxyTextView {
                id("loading")
                title("loading")
            }
            is ListState.Results -> listState.results.forEach {
                ingredient {
                    id(it.id)
                    ingredient(it)
                    isSelected(selectedIngredientIds.contains(it.id))
                    onClick(ingredientClicked)
                }
            }
            ListState.EmptyState -> epoxyTextView {
                id("empty state")
                title("empty state")
            }
            is ListState.Error -> epoxyTextView {
                id("error")
                title("error: ${listState.error.errorMessage}")
            }
        }
    }
}