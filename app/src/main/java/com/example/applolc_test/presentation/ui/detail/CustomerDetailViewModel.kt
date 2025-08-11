package com.example.applolc_test.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.applolc_test.data.repository.CustomerRepository

class CustomerDetailViewModel(private val repository: CustomerRepository) : ViewModel() {

    private val _isVisited = MutableLiveData<Boolean>()
    val isVisited: LiveData<Boolean> = _isVisited

    fun checkVisitedStatus(customerId: Int) {
        _isVisited.value = repository.isCustomerVisited(customerId)
    }

    fun markAsVisited(customerId: Int) {
        repository.markCustomerAsVisited(customerId)
        _isVisited.value = true
    }
}