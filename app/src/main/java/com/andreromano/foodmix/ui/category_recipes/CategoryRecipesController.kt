package com.andreromano.foodmix.ui.category_recipes

import com.airbnb.epoxy.EpoxyAsyncUtil
import com.airbnb.epoxy.EpoxyController
import com.andreromano.foodmix.domain.model.Recipe
import com.andreromano.foodmix.ui.utils.EpoxyModelProperty
import com.andreromano.foodmix.ui.epoxy_models.epoxyTextView
import com.andreromano.foodmix.ui.epoxy_models.recipe
import com.andreromano.foodmix.ui.mapper.errorMessage
import com.andreromano.foodmix.ui.model.ListState

class CategoryRecipesController(
    private val recipeClicked: (Recipe) -> Unit
) : EpoxyController(
    EpoxyAsyncUtil.getAsyncBackgroundHandler(),
    EpoxyAsyncUtil.getAsyncBackgroundHandler()
) {

    var listState: ListState<Recipe> by EpoxyModelProperty { ListState.Loading(null) }

    override fun buildModels() {
        val listState = listState

        when (listState) {
            is ListState.Loading -> epoxyTextView {
                id("loading")
                title("loading")
            }
            is ListState.Results -> listState.results.forEach {
                recipe {
                    id(it.id)
                    recipe(it)
                    onClick(recipeClicked)
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