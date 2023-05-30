package com.varalakshmiakella.republicServices.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DriverViewModelProviderFactory(private val driverRepository: DriverRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DriverViewModel(driverRepository) as T
    }
}