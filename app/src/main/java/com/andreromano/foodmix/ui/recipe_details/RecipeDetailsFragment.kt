package com.andreromano.foodmix.ui.recipe_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.andreromano.foodmix.Injection
import com.andreromano.foodmix.R
import com.andreromano.foodmix.core.EventObserver
import com.andreromano.foodmix.extensions.addOnTabSelectedListener
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.item_category_chip.view.*
import kotlinx.android.synthetic.main.recipe_details_fragment.*

class RecipeDetailsFragment : Fragment(R.layout.recipe_details_fragment) {

    private val args: RecipeDetailsFragmentArgs by navArgs()

    private val viewModel: RecipeDetailsViewModel by viewModels {
        RecipeDetailsViewModel.Factory(
            args.recipe,
            Injection.provideRepository(requireContext())
        )
    }

    private val controller: RecipeDetailsPagerController by lazy {
        RecipeDetailsPagerController(viewModel::addIngredientToShoppingList)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_pager.layoutManager = LinearLayoutManager(requireContext())
        rv_pager.setController(controller)

        tab_layout.addOnTabSelectedListener {
            viewModel.tabSelected(RecipeDetailsContract.ViewState.Tab.values()[it.position])
        }


        viewModel.navigation.observe(viewLifecycleOwner, EventObserver {
            when (it) {
                RecipeDetailsContract.ViewInstruction.NavigateBack -> findNavController().navigateUp()
            }
        })
        viewModel.error.observe(viewLifecycleOwner, EventObserver {

        })
        viewModel.showAddIngredientToShoppingListSuccess.observe(viewLifecycleOwner, Observer {

        })
        viewModel.ingredientsToBeAddedToShoppingListLoading.observe(viewLifecycleOwner, Observer {

        })
        viewModel.isFavorite.observe(viewLifecycleOwner, Observer {

        })
        viewModel.isFavoriteLoading.observe(viewLifecycleOwner, Observer {

        })
        viewModel.imageUrl.observe(viewLifecycleOwner, Observer {
            // TODO: add placeholder later on
            iv_header.load(it)
        })
        viewModel.title.observe(viewLifecycleOwner, Observer {
            tv_title.text = it
        })
        viewModel.description.observe(viewLifecycleOwner, Observer {
            tv_description.text = it
        })
        viewModel.rating.observe(viewLifecycleOwner, Observer {
            rb_rating.rating = it.toFloat()
        })
        viewModel.reviewsCount.observe(viewLifecycleOwner, Observer {
            tv_rating_count.text = "$it reviews"
        })
        viewModel.duration.observe(viewLifecycleOwner, Observer {
            tv_duration.text = "$it min"
        })
        viewModel.calories.observe(viewLifecycleOwner, Observer {
            tv_calories.text = "$it kcal"
        })
        viewModel.servings.observe(viewLifecycleOwner, Observer {
            tv_servings.text = "$it"
        })
        viewModel.categories.observe(viewLifecycleOwner, Observer { categories ->
            cg_categories.removeAllViews()
            categories.forEach { category ->
                val chip = layoutInflater.inflate(R.layout.item_category_chip, cg_categories, false) as Chip
                chip.text = category.name
                cg_categories.addView(chip)
            }
        })
        viewModel.ingredients.observe(viewLifecycleOwner, Observer {
            controller.ingredients = it
        })
        viewModel.directions.observe(viewLifecycleOwner, Observer {
            controller.directions = it
        })
        viewModel.reviews.observe(viewLifecycleOwner, Observer {
            controller.reviews = it
        })
        viewModel.reviewInput.observe(viewLifecycleOwner, Observer {

        })
        viewModel.reviewButtonState.observe(viewLifecycleOwner, Observer {

        })
        viewModel.selectedTab.observe(viewLifecycleOwner, Observer {
            controller.tab = it
        })


    }
}