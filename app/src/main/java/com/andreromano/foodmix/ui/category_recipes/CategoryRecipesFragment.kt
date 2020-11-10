package com.andreromano.foodmix.ui.category_recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.andreromano.foodmix.Injection
import com.andreromano.foodmix.R
import com.andreromano.foodmix.core.EventObserver
import kotlinx.android.synthetic.main.category_recipes_fragment.*

class CategoryRecipesFragment : Fragment(R.layout.category_recipes_fragment) {

    private val args: CategoryRecipesFragmentArgs by navArgs()

    private val viewModel: CategoryRecipesViewModel by viewModels {
        CategoryRecipesViewModel.Factory(
            args.category,
            Injection.provideRepository(requireContext())
        )
    }

    private val controller: CategoryRecipesController by lazy {
        CategoryRecipesController(viewModel::recipeClicked)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_recipes.setControllerAndBuildModels(controller)

        viewModel.navigation.observe(viewLifecycleOwner, EventObserver {
            when (it) {
                is CategoryRecipesContract.ViewInstruction.NavigateToRecipeDetails ->
                    findNavController().navigate(CategoryRecipesFragmentDirections.actionCategoryRecipesToRecipeDetails(it.recipe))
            }
        })

        viewModel.recipes.observe(viewLifecycleOwner, Observer {
            controller.listState = it
        })
    }

}