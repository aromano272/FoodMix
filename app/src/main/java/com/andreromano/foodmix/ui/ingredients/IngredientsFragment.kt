package com.andreromano.foodmix.ui.ingredients

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.andreromano.foodmix.Injection
import com.andreromano.foodmix.R
import com.andreromano.foodmix.core.EventObserver
import com.andreromano.foodmix.domain.model.IngredientType
import com.andreromano.foodmix.extensions.setTextChangedListener
import com.andreromano.foodmix.extensions.setTextWithoutWatcher
import com.andreromano.foodmix.extensions.toVisibility
import com.andreromano.foodmix.ui.mapper.errorMessage
import com.andreromano.foodmix.ui.recipes.RecipesFragmentDirections
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.ingredients_fragment.*


class IngredientsFragment : Fragment(R.layout.ingredients_fragment) {

    private val viewModel: IngredientsViewModel by viewModels {
        IngredientsViewModel.Factory(
            Injection.provideRepository(requireContext())
        )
    }

    private val ingredientsController: IngredientsController by lazy {
        IngredientsController(viewModel::ingredientClicked)
    }

    private val selectedIngredientsAdapter: SelectedIngredientsAdapter by lazy {
        SelectedIngredientsAdapter(viewModel::removeSelectedIngredientClicked)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        replaceIngredientTypeViews(emptyList())

        rv_ingredients.layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
        rv_ingredients.setControllerAndBuildModels(ingredientsController)

        var initialRvIngredientsPaddingBottomPx: Int = 0
        rv_ingredients.post {
            initialRvIngredientsPaddingBottomPx = rv_ingredients.paddingBottom
        }

        rv_selected_ingredients.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        rv_selected_ingredients.adapter = selectedIngredientsAdapter

        // The viewpager was intercepting the scroll event
        rv_selected_ingredients.addOnItemTouchListener(object : OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                val action = e.action
                when (action) {
                    MotionEvent.ACTION_DOWN -> rv.parent.requestDisallowInterceptTouchEvent(true)
                }
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        })

        et_search.setTextChangedListener {
            viewModel.searchQueryInputChanged(it.toString())
        }

        btn_find_recipes.setOnClickListener {
            viewModel.findRecipesClicked()
        }


        viewModel.navigation.observe(viewLifecycleOwner, EventObserver {
            when (it) {
                is IngredientsContract.ViewInstruction.NavigateToSearchRecipesByIngredients ->
                    findNavController().navigate(RecipesFragmentDirections.actionRecipesToSearchRecipesByIngredients(it.ingredients.toTypedArray()))
            }
        })
        viewModel.error.observe(viewLifecycleOwner, EventObserver {
            Snackbar.make(requireView(), it.errorMessage, Snackbar.LENGTH_LONG).show()
        })
        viewModel.searchQueryInput.observe(viewLifecycleOwner) {
            et_search.setTextWithoutWatcher(it)
        }
        viewModel.selectedIngredientType.observe(viewLifecycleOwner) {
            val viewId = ingredientTypesToViewId.getValue(it)
            requireView().findViewById<Chip>(viewId).isChecked = true
        }
        viewModel.ingredientTypes.observe(viewLifecycleOwner) {
            replaceIngredientTypeViews(it)
        }
        viewModel.ingredients.observe(viewLifecycleOwner) {
            ingredientsController.listState = it
        }
        viewModel.selectedIngredients.observe(viewLifecycleOwner) {
            cl_selected_ingredients.toVisibility = it.isNotEmpty()
            ingredientsController.selectedIngredientIds = it.map { it.id }
            selectedIngredientsAdapter.submitList(it)

            val rvIngredientsPaddingPx = if (it.isEmpty()) 0 else resources.getDimensionPixelSize(R.dimen.ingredients_selected_ingredients_height)
            rv_ingredients.setPadding(0, 0, 0, initialRvIngredientsPaddingBottomPx + rvIngredientsPaddingPx)
        }
    }

    private var ingredientTypesToViewId: Map<IngredientType?, Int> = emptyMap()
    private fun replaceIngredientTypeViews(types: List<IngredientType>) {
        ingredientTypesToViewId =
            (listOf<IngredientType?>(null) + types).map { ingredientType ->
                ingredientType to View.generateViewId()
            }.toMap()

        cg_ingredient_types.removeAllViews()
        ingredientTypesToViewId.forEach { (ingredientType, viewId) ->
            val chip = layoutInflater.inflate(R.layout.item_ingredient_type_chip, cg_ingredient_types, false) as Chip
            chip.id = viewId
            chip.text = ingredientType?.name ?: "All"
            chip.isChecked = ingredientType == null
            cg_ingredient_types.addView(chip)
        }

        cg_ingredient_types.setOnCheckedChangeListener { group, checkedId ->
            val ingredientType = ingredientTypesToViewId.filterValues { it == checkedId }.keys.first()
            viewModel.ingredientTypeClicked(ingredientType)
        }

    }

}