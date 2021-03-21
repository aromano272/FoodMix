package com.andreromano.foodmix.ui.create_recipe

import android.content.Context
import android.content.res.ColorStateList
import android.database.DataSetObserver
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.WorkerThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.ListAdapter
import coil.load
import com.andreromano.foodmix.Injection
import com.andreromano.foodmix.R
import com.andreromano.foodmix.core.EventObserver
import com.andreromano.foodmix.domain.model.Ingredient
import com.andreromano.foodmix.extensions.setTextChangedListener
import com.andreromano.foodmix.extensions.setTextWithoutWatcher
import com.andreromano.foodmix.ui.mapper.errorMessage
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.create_recipe_fragment.*
import kotlin.properties.Delegates

class CreateRecipeFragment : Fragment(R.layout.create_recipe_fragment) {

    private val viewModel: CreateRecipeViewModel by viewModels {
        CreateRecipeViewModel.Factory(
            Injection.provideRepository(requireContext())
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = AutoCompleteAdapter()

        val list = listOf(
            "one",
            "two",
            "three",
            "one one",
            "two one",
            "three one",
            "one two",
            "two two",
            "three two",
            "one three",
            "two three",
            "three three",
        )
        val adapterOld: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, list)

        val adapterNu = AutoCompleteBaseAdapter()

        actv_ingredient_input.setAdapter(adapterNu)
        actv_ingredient_input.setTextChangedListener {
            viewModel.newIngredientInputChanged(it.toString())
        }

//        actv_ingredient_input.setOnItemClickListener { _, _, position, _ ->
//            viewModel.ingredientSelectedFromAutocomplete(adapterNu.getItem(position))
//        }

        viewModel.navigation.observe(viewLifecycleOwner, EventObserver {
            when (it) {
                CreateRecipeContract.ViewInstruction.NavigateBack -> TODO()
                CreateRecipeContract.ViewInstruction.OpenCamera -> TODO()
                CreateRecipeContract.ViewInstruction.OpenGallery -> TODO()
                CreateRecipeContract.ViewInstruction.OpenPhotoPicker -> TODO()
                CreateRecipeContract.ViewInstruction.ShowBackConfirmation -> TODO()
            }
        })
        viewModel.error.observe(viewLifecycleOwner, EventObserver {
            Snackbar.make(requireView(), it.errorMessage, Snackbar.LENGTH_LONG).show()
        })
        viewModel.availableIngredientsToPickFrom.observe(viewLifecycleOwner) {
            adapterNu.submitList(it)
        }
        viewModel.nameInput.observe(viewLifecycleOwner) {
            et_recipe_name.setText(it)
        }
        viewModel.attachedImage.observe(viewLifecycleOwner) {
            iv_photo.load(it)
        }
        viewModel.cookingTime.observe(viewLifecycleOwner) {
            tv_cooking_time.text = "$it"
        }
        viewModel.servingsCount.observe(viewLifecycleOwner) {
            tv_servings.text = "$it"
        }
        viewModel.calories.observe(viewLifecycleOwner) {
            // TODO
        }
        viewModel.ingredients.observe(viewLifecycleOwner) {

        }
        viewModel.newIngredientInput.observe(viewLifecycleOwner) {
            if (it.orEmpty() != actv_ingredient_input.text.toString())
                actv_ingredient_input.setTextWithoutWatcher(it)
        }
        viewModel.isNewIngredientInputValid.observe(viewLifecycleOwner) {
            val color = if (it) Color.GREEN else Color.RED
//            actv_ingredient_input.background = ColorDrawable(color)
            actv_ingredient_input.backgroundTintList = ColorStateList.valueOf(color)
        }
        viewModel.directions.observe(viewLifecycleOwner) {

        }
        viewModel.newDirectionTitle.observe(viewLifecycleOwner) {
            et_direction_title.setText(it)
        }
        viewModel.newDirectionDescription.observe(viewLifecycleOwner) {
            et_direction_description.setText(it)
        }
        viewModel.newDirectionAttachedImage.observe(viewLifecycleOwner) {
            iv_direction_photo.load(it)
        }
        viewModel.isCreateRecipeLoading.observe(viewLifecycleOwner) {

        }
    }

    class AutoCompleteBaseAdapter : AbsAutoCompleteBaseAdapter<Ingredient, VH>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
            VH(LayoutInflater.from(parent.context).inflate(android.R.layout.simple_dropdown_item_1line, parent, false))

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.bind(getItem(position).name)
        }

        override fun getItemId(item: Ingredient): Long = item.id.toLong()

        override fun filterItem(item: Ingredient, query: CharSequence): Boolean = item.name.contains(query, ignoreCase = true)

        override fun setTextOnItemSelection(item: Ingredient): String = item.name
    }

    abstract class AbsAutoCompleteBaseAdapter<T, VH : RecyclerView.ViewHolder> : BaseAdapter(), Filterable {
        private var list: List<T> = emptyList()
            set(value) {
                field = value
                filteredList = value
                notifyDataSetChanged()
            }

        private var filteredList: List<T> = emptyList()

        fun submitList(newList: List<T>) {
            list = newList
        }

        abstract fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH

        abstract fun onBindViewHolder(holder: VH, position: Int)

        @WorkerThread
        abstract fun filterItem(item: T, query: CharSequence): Boolean

        abstract fun setTextOnItemSelection(item: T): String

        abstract fun getItemId(item: T): Long


        override fun getCount(): Int = filteredList.size

        override fun getItem(position: Int): T = filteredList[position]

        override fun getItemId(position: Int): Long = getItemId(getItem(position))

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var view by Delegates.notNull<View>()
            var viewHolder by Delegates.notNull<VH>()

            if (convertView == null) {
                viewHolder = onCreateViewHolder(parent, -1)
                view = viewHolder.itemView
                view.tag = viewHolder
            } else {
                view = convertView
                viewHolder = convertView.tag as VH
            }

            onBindViewHolder(viewHolder, position)

            return view
        }

        override fun getFilter(): Filter = object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults = FilterResults().apply {
                val result = if (constraint.isNullOrEmpty()) list else list.filter { filterItem(it, constraint) }
                values = result
                count = result.size
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                @Suppress("UNCHECKED_CAST")
                filteredList = (results?.values as? List<T>) ?: list
                notifyDataSetChanged()
            }

            override fun convertResultToString(resultValue: Any?): CharSequence {
                return this@AbsAutoCompleteBaseAdapter.setTextOnItemSelection(resultValue as T)
            }
        }

        override fun hasStableIds(): Boolean = true
    }

    class AutoCompleteArrayAdapter(context: Context) : ArrayAdapter<Ingredient>(context, android.R.layout.simple_dropdown_item_1line) {

    }

    abstract class AutoCompleteAdapterTest<T>(diffCallback: DiffUtil.ItemCallback<T>) : ListAdapter<T, VH>(diffCallback), android.widget.ListAdapter, Filterable {
        override fun getItem(position: Int): T {
            return super.getItem(position)
        }

        override fun registerDataSetObserver(observer: DataSetObserver) {
            super.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {

            })
        }

        override fun unregisterDataSetObserver(observer: DataSetObserver) {
//            super.unregisterAdapterDataObserver(observer)
        }

        override fun getCount(): Int {
            TODO("Not yet implemented")
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            TODO("Not yet implemented")
        }

        override fun getViewTypeCount(): Int {
            TODO("Not yet implemented")
        }

        override fun isEmpty(): Boolean {
            TODO("Not yet implemented")
        }

        override fun areAllItemsEnabled(): Boolean {
            TODO("Not yet implemented")
        }

        override fun isEnabled(position: Int): Boolean {
            TODO("Not yet implemented")
        }

        override fun getFilter(): Filter {
            TODO("Not yet implemented")
        }
    }


    class AutoCompleteAdapter : ListAdapter<String, VH>(
        object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
        }
    ) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
            VH(LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient, parent, false))

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.bind(super.getItem(position))
        }


    }



    class IngredientViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(item: Ingredient, onClick: (Ingredient) -> Unit) {
            containerView.setOnClickListener {
                onClick(item)
            }

            containerView.findViewById<TextView>(android.R.id.text1).text = item.name
        }
    }

    class VH(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(item: String) {
            containerView.findViewById<TextView>(android.R.id.text1).text = item
        }
    }
}