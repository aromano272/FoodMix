package com.andreromano.foodmix.ui.categories

import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.andreromano.foodmix.Injection
import com.andreromano.foodmix.R
import com.andreromano.foodmix.core.EventObserver
import com.andreromano.foodmix.extensions.setTextChangedListener
import com.andreromano.foodmix.extensions.setTextWithoutWatcher
import kotlinx.android.synthetic.main.categories_fragment.*

class CategoriesFragment : Fragment(R.layout.categories_fragment) {

    private val viewModel: CategoriesViewModel by viewModels {
        CategoriesViewModel.Factory(
            Injection.provideRepository(requireContext())
        )
    }

    private val controller: CategoriesController by lazy {
        CategoriesController(viewModel::categoryClicked)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_categories.setControllerAndBuildModels(controller)

        et_search.setTextChangedListener {
            viewModel.searchQueryInputChanged(it.toString())
        }

        viewModel.navigation.observe(viewLifecycleOwner, EventObserver {
            when (it) {
                is CategoriesContract.ViewInstruction.NavigateToCategoryRecipes ->
                    findNavController().navigate(CategoriesFragmentDirections.actionCategoriesToCategoryRecipes(it.category))
            }
        })

        viewModel.searchQueryInput.observe(viewLifecycleOwner, Observer {
            if (it.orEmpty() != et_search.text.toString()) {
                et_search.setTextWithoutWatcher(it)
            }
        })

        viewModel.categories.observe(viewLifecycleOwner, Observer {
            controller.listState = it
        })
    }

}