package com.andreromano.foodmix.ui.epoxy_models

import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.andreromano.foodmix.R
import com.andreromano.foodmix.domain.model.Ingredient
import com.andreromano.foodmix.extensions.toVisibility


@EpoxyModelClass
abstract class IngredientModel : EpoxyModelWithHolder<IngredientModel.Holder>() {

    override fun getDefaultLayout(): Int = R.layout.item_ingredient

    @EpoxyAttribute
    lateinit var ingredient: Ingredient

    @JvmField
    @EpoxyAttribute
    var isSelected: Boolean = false

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onClick: (Ingredient) -> Unit


    override fun bind(holder: Holder) = with(holder) {
        iv_image.load(ingredient.imageUrl)
        tv_name.text = ingredient.name

        iv_checked.toVisibility = isSelected

        view.setOnClickListener { onClick(ingredient) }
    }

    class Holder : KotlinEpoxyHolder() {
        val iv_image by bind<ImageView>(R.id.iv_image)
        val tv_name by bind<TextView>(R.id.tv_name)
        val iv_checked by bind<ImageView>(R.id.iv_checked)
    }
}