package com.andreromano.foodmix.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.andreromano.foodmix.core.Event
import com.andreromano.foodmix.data.UserProfileRepository
import com.andreromano.foodmix.extensions.launch
import com.andreromano.foodmix.extensions.shareHere
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.mapNotNull

class AccountViewModel(
    private val userProfileRepository: UserProfileRepository
) : ViewModel(), AccountContract.ViewModel {

    private val _navigation = MutableSharedFlow<AccountContract.ViewInstructions>()
    override val navigation: LiveData<Event<AccountContract.ViewInstructions>> = _navigation.mapLatest { Event(it) }.asLiveData()

    private val userProfileResult = userProfileRepository.get().shareHere(this)
    private val userProfileData = userProfileResult.mapNotNull { it.data }.shareHere(this)

    override val backgroundUrl: LiveData<String> = userProfileData.mapLatest { it.backgroundUrl }.asLiveData()

    override val avatarUrl: LiveData<String> = userProfileData.mapLatest { it.avatarUrl }.asLiveData()

    override val username: LiveData<String> = userProfileData.mapLatest { it.username }.asLiveData()

    override val description: LiveData<String> = userProfileData.mapLatest { it.description }.asLiveData()

    override val totalRecipesCount: LiveData<Int> = userProfileData.mapLatest { it.totalRecipesCount }.asLiveData()

    override val totalCookbooksCount: LiveData<Int> = userProfileData.mapLatest { it.totalCookbooksCount }.asLiveData()

    override val myRecipesCount: LiveData<Int> = userProfileData.mapLatest { it.myRecipesCount }.asLiveData()

    override val myCookbooksCount: LiveData<Int> = userProfileData.mapLatest { it.myCookbooksCount }.asLiveData()

    override val shoppingListCount: LiveData<Int> = userProfileData.mapLatest { it.shoppingListCount }.asLiveData()

    override fun myRecipesClicked() = launch {
        _navigation.emit(AccountContract.ViewInstructions.NavigateToMyRecipes)
    }

    override fun myCookbooksClicked() = launch {
        _navigation.emit(AccountContract.ViewInstructions.NavigateToMyCookbooks)
    }

    override fun cookbooksClicked() = launch {
        _navigation.emit(AccountContract.ViewInstructions.NavigateToSavedCookbooks)
    }

    override fun recipesClicked() = launch {
        _navigation.emit(AccountContract.ViewInstructions.NavigateToSavedRecipes)
    }

    override fun shoppingListClicked() = launch {
        _navigation.emit(AccountContract.ViewInstructions.NavigateToShoppingList)
    }

    override fun addRecipeClicked() = launch {
        _navigation.emit(AccountContract.ViewInstructions.NavigateToAddRecipe)
    }

    override fun createCookbookClicked() = launch {
        _navigation.emit(AccountContract.ViewInstructions.NavigateToCreateCookbook)
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val userProfileRepository: UserProfileRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return AccountViewModel(
                userProfileRepository
            ) as T
        }
    }

}