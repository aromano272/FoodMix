package com.andreromano.foodmix.ui.category_recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.andreromano.foodmix.Injection
import com.andreromano.foodmix.R
import com.andreromano.foodmix.core.EventObserver
import com.andreromano.foodmix.network.FakeData
import com.andreromano.foodmix.ui.model.ListState
import kotlinx.android.synthetic.main.category_recipes_fragment.*

class CategoryRecipesFragment : Fragment(R.layout.category_recipes_fragment) {

    private val args: CategoryRecipesFragmentArgs by navArgs()

    private val viewModel: CategoryRecipesViewModel by viewModels {
        CategoryRecipesViewModel.Factory(
            FakeData.categories.first(),
            Injection.provideRecipeRepository(requireContext())
        )
    }

    private val adapter: CategoryRecipesAdapter by lazy {
        CategoryRecipesAdapter(viewModel::recipeClicked)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_categories.layoutManager = LinearLayoutManager(requireContext())
        rv_categories.adapter = adapter

        viewModel.navigation.observe(viewLifecycleOwner, EventObserver {
            when (it) {
                is CategoryRecipesContract.ViewInstruction.NavigateToRecipeDetails -> TODO()
            }
        })

        viewModel.recipes.observe(viewLifecycleOwner, Observer {
            when (it) {
                ListState.Loading -> {}
                is ListState.Results -> adapter.submitList(it.results)
                ListState.EmptyState -> {}
                is ListState.Error -> {}
            }
        })
    }

}