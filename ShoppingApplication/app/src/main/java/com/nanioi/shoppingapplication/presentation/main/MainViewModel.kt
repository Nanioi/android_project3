package com.nanioi.shoppingapplication.presentation.main

import androidx.lifecycle.viewModelScope
import com.nanioi.shoppingapplication.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class MainViewModel : BaseViewModel() {


    override fun fetchData(): Job = viewModelScope.launch {
        TODO("Not yet implemented")
    }
}