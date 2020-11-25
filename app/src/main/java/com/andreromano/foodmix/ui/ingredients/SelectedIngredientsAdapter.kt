package com.andreromano.foodmix.ui.ingredients

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.andreromano.foodmix.R
import com.andreromano.foodmix.domain.model.Ingredient
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_selected_ingredient.view.*

class SelectedIngredientsAdapter(
    private val removeIngredientClicked: (Ingredient) -> Unit
) : ListAdapter<Ingredient, SelectedIngredientsAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Ingredient>() {
        override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean = oldItem == newItem
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_selected_ingredient, parent, false), removeIngredientClicked)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    class ViewHolder(
        override val containerView: View,
        private val removeIngredientClicked: (Ingredient) -> Unit
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(ingredient: Ingredient) = with (containerView) {
            iv_image.load(ingredient.imageUrl)

            setOnClickListener {
                removeIngredientClicked(ingredient)
            }
        }
    }

}