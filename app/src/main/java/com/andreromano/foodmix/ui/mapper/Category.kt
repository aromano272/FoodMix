package com.andreromano.foodmix.ui.mapper

import androidx.annotation.ColorRes
import com.andreromano.foodmix.domain.model.Category
import com.andreromano.foodmix.ui.App
import kotlin.math.absoluteValue


@get:ColorRes
val Category.colorResId: Int
    get() = getMatColor(this)


@ColorRes
private fun getMatColor(category: Category): Int {
    val colorGamut = listOf(
        "red", "deep_purple", "light_blue", "green", "yellow", "deep_orange", "blue_grey", "pink", "indigo",
        "cyan", "light_green", "amber", "brown", "purple", "blue", "teal", "lime", "orange", "grey"
    )
    val colorRanges = listOf("50", "100", "200", "300", "400", "500", "600", "700", "800", "900")

    val chosenGamitIndex = category.id.hashCode().absoluteValue % colorGamut.size
    val chosenRangeIndex = category.id.hashCode().absoluteValue % colorRanges.size

    val chosenGamut = colorGamut[chosenGamitIndex]
    val chosenRange = colorRanges[chosenRangeIndex]

    return App.context.resources.getIdentifier("${chosenGamut}_${chosenRange}", "color", App.context.applicationContext.packageName)
}