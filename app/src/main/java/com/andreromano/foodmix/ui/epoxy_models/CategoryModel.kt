package com.andreromano.foodmix.ui.epoxy_models

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.andreromano.foodmix.R
import com.andreromano.foodmix.domain.model.Category


@EpoxyModelClass(layout = R.layout.item_category)
abstract class CategoryModel : EpoxyModelWithHolder<CategoryModel.Holder>() {

    @EpoxyAttribute
    lateinit var category: Category

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onClick: (Category) -> Unit


    override fun bind(holder: Holder) = with (holder) {
        tv_image.text = "categories = ${category.imageUrl}"
        tv_name.text = "title = ${category.name}"

        view.setOnClickListener { onClick(category) }
    }

    class Holder : KotlinEpoxyHolder() {
        val tv_image by bind<TextView>(R.id.tv_image)
        val tv_name by bind<TextView>(R.id.tv_name)
    }

}