package com.andreromano.foodmix.ui.categories

import com.airbnb.epoxy.EpoxyAsyncUtil
import com.airbnb.epoxy.EpoxyController
import com.andreromano.foodmix.domain.model.Category
import com.andreromano.foodmix.ui.epoxy_models.category
import com.andreromano.foodmix.ui.epoxy_models.epoxyTextView
import com.andreromano.foodmix.ui.mapper.errorMessage
import com.andreromano.foodmix.ui.model.ListState
import com.andreromano.foodmix.ui.utils.EpoxyModelProperty

class CategoriesController(
    private val categoryClicked: (Category) -> Unit
) : EpoxyController(
    EpoxyAsyncUtil.getAsyncBackgroundHandler(),
    EpoxyAsyncUtil.getAsyncBackgroundHandler()
) {

    var listState: ListState<Category> by EpoxyModelProperty { ListState.Loading(null) }

    override fun buildModels() {
        val listState = listState

        when (listState) {
            is ListState.Loading ->
                if (listState.results != null) showResults(listState.results.results)
                else epoxyTextView {
                    id("loading")
                    title("loading")
                }
            is ListState.Results -> showResults(listState.results)
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

    private fun showResults(results: List<Category>) = results.forEach {
        category {
            id(it.id)
            category(it)
            onClick(categoryClicked)
        }
    }
}