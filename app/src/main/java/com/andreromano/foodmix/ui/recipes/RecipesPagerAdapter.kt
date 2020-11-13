package com.andreromano.foodmix.ui.recipes

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.andreromano.foodmix.ui.categories.CategoriesFragment
import com.andreromano.foodmix.ui.ingredients.IngredientsFragment

class RecipesPagerAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    enum class Tab {
        CATEGORIES,
        INGREDIENTS
    }

    override fun getItemCount(): Int = Tab.values().size

    override fun createFragment(position: Int): Fragment = when (Tab.values()[position]) {
        Tab.CATEGORIES -> CategoriesFragment()
        Tab.INGREDIENTS -> IngredientsFragment()
    }
}