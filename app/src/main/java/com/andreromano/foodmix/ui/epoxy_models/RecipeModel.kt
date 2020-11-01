package com.andreromano.foodmix.ui.epoxy_models

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.andreromano.foodmix.R
import com.andreromano.foodmix.domain.model.Recipe


@EpoxyModelClass(layout = R.layout.item_recipe)
abstract class RecipeModel : EpoxyModelWithHolder<RecipeModel.Holder>() {

    @EpoxyAttribute
    lateinit var recipe: Recipe

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onClick: (Recipe) -> Unit


    override fun bind(holder: Holder) = with (holder) {
        tv_categories.text = "categories = ${recipe.categories}"
        tv_title.text = "title = ${recipe.title}"
        tv_rating.text = "rating = ${recipe.rating}"
        tv_rating_count.text = "rating_count = ${recipe.ratingsCount}"
        tv_duration.text = "duration = ${recipe.duration}"
        tv_calories.text = "calories = ${recipe.calories}"

        view.setOnClickListener { onClick(recipe) }
    }

    class Holder : KotlinEpoxyHolder() {
        val tv_categories by bind<TextView>(R.id.tv_categories)
        val tv_title by bind<TextView>(R.id.tv_title)
        val tv_rating by bind<TextView>(R.id.tv_rating)
        val tv_rating_count by bind<TextView>(R.id.tv_rating_count)
        val tv_duration by bind<TextView>(R.id.tv_duration)
        val tv_calories by bind<TextView>(R.id.tv_calories)
    }

}