package com.andreromano.foodmix.ui.mapper

import com.andreromano.foodmix.domain.model.IngredientType


val IngredientType.string: String
    get() = when (this) {
        IngredientType.MEAT -> "Meat"
        IngredientType.FISH -> "Fish"
        IngredientType.VEGETABLES -> "Vegetables"
        IngredientType.FRUITS -> "Fruits"
        IngredientType.GRAINS -> "Grains"
    }