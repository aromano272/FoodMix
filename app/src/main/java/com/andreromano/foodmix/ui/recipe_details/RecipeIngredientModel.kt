package com.andreromano.foodmix.ui.recipe_details


import android.widget.ImageButton
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.andreromano.foodmix.R
import com.andreromano.foodmix.domain.model.Ingredient
import com.andreromano.foodmix.ui.epoxy_models.KotlinEpoxyHolder


@EpoxyModelClass
abstract class RecipeIngredientModel : EpoxyModelWithHolder<RecipeIngredientModel.Holder>() {

    override fun getDefaultLayout(): Int = R.layout.item_recipe_ingredient

    @EpoxyAttribute
    lateinit var ingredient: Ingredient

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onAddClick: (Ingredient) -> Unit


    override fun bind(holder: Holder) = with (holder) {
        tv_ingredient.text = ingredient.name

        ib_add_ingredient.setOnClickListener { onAddClick(ingredient) }
    }

    class Holder : KotlinEpoxyHolder() {
        val ib_add_ingredient by bind<ImageButton>(R.id.ib_add_ingredient)
        val tv_ingredient by bind<TextView>(R.id.tv_ingredient)
    }

}