package com.andreromano.foodmix.ui.recipe_details


import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.andreromano.foodmix.R
import com.andreromano.foodmix.domain.model.Direction
import com.andreromano.foodmix.ui.epoxy_models.KotlinEpoxyHolder


@EpoxyModelClass
abstract class RecipeDirectionModel : EpoxyModelWithHolder<RecipeDirectionModel.Holder>() {

    override fun getDefaultLayout(): Int = R.layout.item_recipe_direction

    @EpoxyAttribute
    lateinit var direction: Direction

    override fun bind(holder: Holder) = with(holder) {
        iv_header.load(direction.imageUrl)

        tv_title.text = direction.title
        tv_description.text = direction.description
    }

    class Holder : KotlinEpoxyHolder() {
        val iv_header by bind<ImageView>(R.id.iv_header)
        val tv_title by bind<TextView>(R.id.tv_title)
        val tv_description by bind<TextView>(R.id.tv_description)
    }
}