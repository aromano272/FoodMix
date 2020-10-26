package com.andreromano.foodmix.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel



    @Suppress("UNCHECKED_CAST")
    class Factory(

    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(
            ) as T
        }
    }

}