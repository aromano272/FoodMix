package com.andreromano.foodmix.ui.categories

import androidx.lifecycle.*
import com.andreromano.foodmix.core.Event
import com.andreromano.foodmix.core.ResultKt
import com.andreromano.foodmix.data.Repository
import com.andreromano.foodmix.domain.model.Category
import com.andreromano.foodmix.extensions.launch
import com.andreromano.foodmix.ui.model.ListState
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val repository: Repository
) : ViewModel(), CategoriesContract.ViewModel {

    private val _navigation = MutableLiveData<Event<CategoriesContract.ViewInstruction>>()
    override val navigation: LiveData<Event<CategoriesContract.ViewInstruction>> = _navigation

    private val _searchQueryInput = MutableLiveData<String>()
    override val searchQueryInput: LiveData<String> = _searchQueryInput

    private val _categories = MutableLiveData<ListState<Category>>()
    override val categories: LiveData<ListState<Category>> = _categories

    init {
        fetchCategoriesAndUpdateUi("")
    }

    private fun fetchCategoriesAndUpdateUi(searchQuery: String) = launch {
        val result = repository.getCategories(searchQuery)

        val newValue = when (result) {
            is ResultKt.Success ->  if (result.data.isEmpty()) ListState.EmptyState else ListState.Results(result.data)
            is ResultKt.Failure -> ListState.Error(result.error)
        }

        _categories.value = newValue
    }

    override fun searchQueryInputChanged(query: String) {
        _searchQueryInput.value = query
        fetchCategoriesAndUpdateUi(query)
    }

    override fun categoryClicked(category: Category) {
        _navigation.value = Event(CategoriesContract.ViewInstruction.NavigateToCategoryRecipes(category))
    }


    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val repository: Repository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CategoriesViewModel(
                repository
            ) as T
        }
    }
}