package com.example.applolc_test.presentation.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applolc_test.data.model.Customer
import com.example.applolc_test.data.repository.CustomerRepository
import kotlinx.coroutines.launch

class CustomerListViewModel(private val repository: CustomerRepository) : ViewModel() {

    private val _customers = MutableLiveData<List<Customer>>()
    val customers: LiveData<List<Customer>> = _customers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadCustomers() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            repository.getCustomers().fold(
                onSuccess = { customerList ->
                    _customers.value = customerList
                    _isLoading.value = false
                },
                onFailure = { exception ->
                    _error.value = exception.message
                    _isLoading.value = false
                }
            )
        }
    }

    fun isCustomerVisited(customerId: Int): Boolean {
        return repository.isCustomerVisited(customerId)
    }
}
