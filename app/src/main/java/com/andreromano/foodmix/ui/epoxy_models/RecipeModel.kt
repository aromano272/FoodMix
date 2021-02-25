package com.andreromano.foodmix.ui.epoxy_models

import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.andreromano.foodmix.R
import com.andreromano.foodmix.domain.model.Recipe
import com.andreromano.foodmix.ui.mapper.colorResId
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.willy.ratingbar.BaseRatingBar


@EpoxyModelClass
abstract class RecipeModel : EpoxyModelWithHolder<RecipeModel.Holder>() {

    override fun getDefaultLayout(): Int = R.layout.item_recipe

    @EpoxyAttribute
    lateinit var recipe: Recipe

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onClick: (Recipe) -> Unit


    override fun bind(holder: Holder) = with(holder) {
        iv_image.load(recipe.imageUrl)
        tv_title.text = recipe.title
        tv_rating_count.text = "${recipe.ratingsCount}"
        tv_duration.text = "${recipe.cookingTime} min"
        tv_calories.text = "${recipe.calories} kcal"

        rb_rating.rating = recipe.rating.toFloat()

        val layoutInflater = LayoutInflater.from(cg_categories.context)
        cg_categories.removeAllViews()
        recipe.categories.forEach { category ->
            val chip = layoutInflater.inflate(R.layout.item_category_chip, cg_categories, false) as Chip
            chip.text = category.name
            chip.setChipBackgroundColorResource(category.colorResId)
            cg_categories.addView(chip)
        }

        view.setOnClickListener { onClick(recipe) }
    }

    class Holder : KotlinEpoxyHolder() {
        val cg_categories by bind<ChipGroup>(R.id.cg_categories)
        val tv_title by bind<TextView>(R.id.tv_title)
        val iv_image by bind<ImageView>(R.id.iv_image)
        val rb_rating by bind<BaseRatingBar>(R.id.rb_rating)
        val tv_rating_count by bind<TextView>(R.id.tv_rating_count)
        val tv_duration by bind<TextView>(R.id.tv_duration)
        val tv_calories by bind<TextView>(R.id.tv_calories)
    }
}