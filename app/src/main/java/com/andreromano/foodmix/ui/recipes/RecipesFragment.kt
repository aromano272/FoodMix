package com.andreromano.foodmix.ui.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.andreromano.foodmix.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.recipes_fragment.*

class RecipesFragment : Fragment(R.layout.recipes_fragment) {

    private lateinit var recipesPagerAdapter: RecipesPagerAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recipesPagerAdapter = RecipesPagerAdapter(this)
        vp_container.adapter = recipesPagerAdapter

        TabLayoutMediator(tab_layout, vp_container) { tab, position ->
            vp_container.setCurrentItem(tab.position, true)
            val text = when (RecipesPagerAdapter.Tab.values()[position]) {
                RecipesPagerAdapter.Tab.CATEGORIES -> "Categories"
                RecipesPagerAdapter.Tab.INGREDIENTS -> "Ingredients"
            }
            tab.text = text
        }.attach()

    }

    override fun onDestroyView() {
        vp_container.adapter = null
        super.onDestroyView()
    }
}