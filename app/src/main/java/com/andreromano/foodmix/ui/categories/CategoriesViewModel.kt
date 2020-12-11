package com.andreromano.foodmix.ui.categories

import androidx.lifecycle.*
import com.andreromano.foodmix.core.ErrorKt
import com.andreromano.foodmix.core.Event
import com.andreromano.foodmix.core.Resource
import com.andreromano.foodmix.data.Repository
import com.andreromano.foodmix.domain.model.Category
import com.andreromano.foodmix.extensions.asListState
import com.andreromano.foodmix.extensions.filterResourceFailure
import com.andreromano.foodmix.extensions.map
import com.andreromano.foodmix.extensions.shareHere
import com.andreromano.foodmix.ui.mapper.toListState
import com.andreromano.foodmix.ui.model.ListState
import kotlinx.coroutines.flow.*
import timber.log.Timber

class CategoriesViewModel(
    private val repository: Repository
) : ViewModel(), CategoriesContract.ViewModel {

    private val _navigation = MutableLiveData<CategoriesContract.ViewInstruction>()
    override val navigation: LiveData<Event<CategoriesContract.ViewInstruction>> = _navigation.map { Event(it) }

    private val _searchQueryInput = MutableStateFlow("")
    override val searchQueryInput: LiveData<String> = _searchQueryInput.asLiveData()

    private val categoriesResult: SharedFlow<Resource<List<Category>>> = _searchQueryInput.debounce(300).flatMapLatest { query ->
        repository.getCategories(query)
    }.shareHere(this)

    override val categories: LiveData<ListState<Category>> = categoriesResult.asListState().asLiveData()

    override val error: LiveData<Event<ErrorKt>> =
        categoriesResult
            .filterResourceFailure()
            .mapLatest { Event(it.error) }
            .asLiveData()

    override fun searchQueryInputChanged(query: String) {
        _searchQueryInput.value = query
    }

    override fun categoryClicked(category: Category) {
        _navigation.value = CategoriesContract.ViewInstruction.NavigateToCategoryRecipes(category)
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