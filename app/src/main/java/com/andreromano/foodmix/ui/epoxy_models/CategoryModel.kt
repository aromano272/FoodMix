package com.andreromano.foodmix.ui.epoxy_models

import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.andreromano.foodmix.R
import com.andreromano.foodmix.domain.model.Category


@EpoxyModelClass
abstract class CategoryModel : EpoxyModelWithHolder<CategoryModel.Holder>() {

    override fun getDefaultLayout(): Int = R.layout.item_category

    @EpoxyAttribute
    lateinit var category: Category

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onClick: (Category) -> Unit


    override fun bind(holder: Holder) = with (holder) {
        // TODO: placeholder
        iv_image.load(category.imageUrl)
        tv_name.text = category.name

        view.setOnClickListener { onClick(category) }
    }

    class Holder : KotlinEpoxyHolder() {
        val iv_image by bind<ImageView>(R.id.iv_image)
        val tv_name by bind<TextView>(R.id.tv_name)
    }

}