package com.andreromano.foodmix.ui.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.andreromano.foodmix.Injection
import com.andreromano.foodmix.R
import com.andreromano.foodmix.core.EventObserver
import kotlinx.android.synthetic.main.account_fragment.*

class AccountFragment : Fragment(R.layout.account_fragment) {

    private val viewModel: AccountViewModel by viewModels {
        AccountViewModel.Factory(
            Injection.provideUserProfileRepository(requireContext())
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fl_recipes_count.setOnClickListener {
            viewModel.recipesClicked()
        }
        fl_cookbooks_count.setOnClickListener {
            viewModel.cookbooksClicked()
        }
        fl_my_recipes.setOnClickListener {
            viewModel.myRecipesClicked()
        }
        fl_my_cookbooks.setOnClickListener {
            viewModel.myCookbooksClicked()
        }
        fl_shopping_list.setOnClickListener {
            viewModel.shoppingListClicked()
        }
        btn_add_recipe.setOnClickListener {
            viewModel.addRecipeClicked()
        }
        btn_create_cookbook.setOnClickListener {
            viewModel.createCookbookClicked()
        }

        viewModel.navigation.observe(viewLifecycleOwner, EventObserver {
            when (it) {
                AccountContract.ViewInstruction.NavigateToMyRecipes -> TODO()
                AccountContract.ViewInstruction.NavigateToMyCookbooks -> TODO()
                AccountContract.ViewInstruction.NavigateToSavedRecipes -> TODO()
                AccountContract.ViewInstruction.NavigateToSavedCookbooks -> TODO()
                AccountContract.ViewInstruction.NavigateToShoppingList -> TODO()
                AccountContract.ViewInstruction.NavigateToCreateRecipe -> findNavController().navigate(AccountFragmentDirections.actionAccountToCreateRecipe())
                AccountContract.ViewInstruction.NavigateToCreateCookbook -> TODO()
            }
        })
        viewModel.backgroundUrl.observe(viewLifecycleOwner) {
            iv_background.load(it)
        }
        viewModel.avatarUrl.observe(viewLifecycleOwner) {
            iv_avatar.load(it)
        }
        viewModel.username.observe(viewLifecycleOwner) {
            tv_username.text = it
        }
        viewModel.description.observe(viewLifecycleOwner) {
            tv_description.text = it
        }
        viewModel.totalRecipesCount.observe(viewLifecycleOwner) {
            tv_recipes_count.text = "$it"
        }
        viewModel.totalCookbooksCount.observe(viewLifecycleOwner) {
            tv_cookbooks_count.text = "$it"
        }
        viewModel.myRecipesCount.observe(viewLifecycleOwner) {
            tv_my_recipes_count.text = "$it"
        }
        viewModel.myCookbooksCount.observe(viewLifecycleOwner) {
            tv_my_cookbooks_count.text = "$it"
        }
        viewModel.shoppingListCount.observe(viewLifecycleOwner) {
            tv_shopping_list_count.text = "$it"
        }

    }
}