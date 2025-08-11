package com.example.applolc_test.presentation.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.applolc_test.data.repository.CustomerRepository
import com.example.applolc_test.presentation.ui.detail.CustomerDetailViewModel
import com.example.applolc_test.presentation.ui.list.CustomerListViewModel

class ViewModelFactory(private val repository: CustomerRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CustomerListViewModel::class.java) -> {
                CustomerListViewModel(repository) as T
            }
            modelClass.isAssignableFrom(CustomerDetailViewModel::class.java) -> {
                CustomerDetailViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}