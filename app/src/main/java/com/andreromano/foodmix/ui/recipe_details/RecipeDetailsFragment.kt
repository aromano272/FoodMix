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
import com.andreromano.foodmix.extensions.setTextChangedListener
import com.andreromano.foodmix.extensions.setTextWithoutWatcher
import com.andreromano.foodmix.extensions.toVisibility
import com.andreromano.foodmix.ui.mapper.errorMessage
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
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
        RecipeDetailsPagerController(
            viewModel::addIngredientToShoppingList,
            viewModel::reviewUserClicked,
            viewModel::reviewFavoriteClicked,
            viewModel::reviewReplyClicked
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_pager.layoutManager = LinearLayoutManager(requireContext())
        rv_pager.setHasFixedSize(false)
        rv_pager.setController(controller)


        btn_nav_back.setOnClickListener {
            viewModel.backClicked()
        }
        btn_action.setOnClickListener {
            viewModel.favoriteClicked()
        }
        tab_layout.addOnTabSelectedListener {
            viewModel.tabSelected(RecipeDetailsContract.ViewState.Tab.values()[it.position])
        }
        btn_review_attach.setOnClickListener {
            // TODO
        }
        et_review_input.setTextChangedListener {
            viewModel.reviewInputChanged(it.toString())
        }
        btn_send_review.setOnClickListener {
            viewModel.sendReviewClicked()
        }


        viewModel.navigation.observe(viewLifecycleOwner, EventObserver {
            when (it) {
                RecipeDetailsContract.ViewInstruction.NavigateBack -> findNavController().navigateUp()
            }
        })
        viewModel.error.observe(viewLifecycleOwner, EventObserver {
            Snackbar.make(requireView(), it.errorMessage, Snackbar.LENGTH_LONG).show()
        })
        viewModel.showAddIngredientToShoppingListSuccess.observe(viewLifecycleOwner, EventObserver { ingredient ->
            Snackbar.make(requireView(), "Added $ingredient to shopping list!", Snackbar.LENGTH_LONG).show()
        })
        viewModel.ingredientsToBeAddedToShoppingListLoading.observe(viewLifecycleOwner, Observer {
            // TODO
        })
        viewModel.isFavorite.observe(viewLifecycleOwner, Observer {
            btn_action.setImageResource(if (it) R.drawable.ic_favorite_filled_24 else R.drawable.ic_favorite_empty_24)
        })
        viewModel.isFavoriteLoading.observe(viewLifecycleOwner, Observer {
            // TODO
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
            if (it.orEmpty() != et_review_input.text.toString()) {
                et_review_input.setTextWithoutWatcher(it)
            }
        })
        viewModel.reviewButtonState.observe(viewLifecycleOwner, Observer {
            pb_send_review.toVisibility = it == RecipeDetailsContract.ViewState.ButtonState.LOADING
            btn_send_review.toVisibility = it != RecipeDetailsContract.ViewState.ButtonState.LOADING
            btn_send_review.isEnabled = it == RecipeDetailsContract.ViewState.ButtonState.ENABLED
            btn_send_review.imageTintList
        })
        viewModel.selectedTab.observe(viewLifecycleOwner, Observer {
            controller.tab = it
            cl_review_input.toVisibility = it == RecipeDetailsContract.ViewState.Tab.REVIEWS
            val padding =
                if (it == RecipeDetailsContract.ViewState.Tab.REVIEWS) resources.getDimension(R.dimen.recipe_details_review_input_height).toInt()
                else 0
            rv_pager.setPaddingRelative(0, 0, 0, padding)
        })
    }
}