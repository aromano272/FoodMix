package com.andreromano.foodmix.ui.search_recipes_by_ingredients

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.View
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.andreromano.foodmix.Injection
import com.andreromano.foodmix.R
import com.andreromano.foodmix.core.EventObserver
import com.andreromano.foodmix.domain.model.RecipesOrderBy
import com.andreromano.foodmix.extensions.toVisibility
import kotlinx.android.synthetic.main.search_recipes_by_ingredient_fragment.*

class SearchRecipesByIngredientsFragment : Fragment(R.layout.search_recipes_by_ingredient_fragment) {

    private val args: SearchRecipesByIngredientsFragmentArgs by navArgs()

    private val viewModel: SearchRecipesByIngredientsViewModel by viewModels {
        SearchRecipesByIngredientsViewModel.Factory(
            args.ingredients.toList(),
            Injection.provideRepository(requireContext())
        )
    }

    private val controller: SearchRecipesByIngredientsController by lazy {
        SearchRecipesByIngredientsController(viewModel::recipeClicked)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_recipes.setController(controller)

        btn_nav_back.setOnClickListener {
            viewModel.backClicked()
        }

        fl_order_by_relevance.setOnClickListener {
            viewModel.orderByChanged(RecipesOrderBy.RELEVANCE)
        }
        fl_order_by_rating.setOnClickListener {
            viewModel.orderByChanged(RecipesOrderBy.RATING)
        }
        fl_order_by_duration.setOnClickListener {
            viewModel.orderByChanged(RecipesOrderBy.DURATION)
        }

        viewModel.navigation.observe(viewLifecycleOwner, EventObserver {
            when (it) {
                is SearchRecipesByIngredientsContract.ViewInstruction.NavigateToRecipeDetails ->
                    findNavController().navigate(SearchRecipesByIngredientsFragmentDirections.actionSearchRecipesByIngredientsToRecipeDetails(it.recipe))
                SearchRecipesByIngredientsContract.ViewInstruction.NavigateBack -> findNavController().navigateUp()
            }
        })
        viewModel.orderBy.observe(viewLifecycleOwner) {
            chip_order_by_relevance.isChecked = it == RecipesOrderBy.RELEVANCE
            chip_order_by_rating.isChecked = it == RecipesOrderBy.RATING
            chip_order_by_duration.isChecked = it == RecipesOrderBy.DURATION
        }
        viewModel.searchDetailsInfo.observe(viewLifecycleOwner) { info ->
            tv_search_details_info.visibility = if (info != null) View.VISIBLE else View.INVISIBLE
            if (info != null) {
                val firstPart = "${info.total} search results for "
                val secondPart = info.searchQueries.joinToString(", ")
                val spannable = SpannableStringBuilder("$firstPart$secondPart").apply {
                    setSpan(StyleSpan(Typeface.BOLD), firstPart.length, firstPart.length + secondPart.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                }

                tv_search_details_info.text = spannable
            }
        }
        viewModel.results.observe(viewLifecycleOwner) {
            controller.listState = it
        }
    }

}