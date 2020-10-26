package com.andreromano.foodmix.ui.category_recipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andreromano.foodmix.R
import com.andreromano.foodmix.domain.model.Recipe
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_recipe.view.*

class CategoryRecipesAdapter(
    private val onClick: (Recipe) -> Unit
) : ListAdapter<Recipe, CategoryRecipesAdapter.VH>(
    object : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean = oldItem == newItem
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    class VH(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(item: Recipe, onClick: (Recipe) -> Unit) = with (containerView) {
            tv_categories.text = "categories = ${item.categories}"
            tv_title.text = "title = ${item.title}"
            tv_rating.text = "rating = ${item.rating}"
            tv_rating_count.text = "rating_count = ${item.ratingsCount}"
            tv_duration.text = "duration = ${item.duration}"
            tv_calories.text = "calories = ${item.calories}"

            setOnClickListener { onClick(item) }
        }
    }

}