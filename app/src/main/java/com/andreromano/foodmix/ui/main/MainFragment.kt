package com.andreromano.foodmix.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.andreromano.foodmix.R

class MainFragment : Fragment(R.layout.main_fragment) {

    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory() }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

}