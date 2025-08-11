package com.example.applolc_test.presentation.ui.list

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.applolc_test.CustomerApplication
import com.example.applolc_test.data.model.Customer
import com.example.applolc_test.databinding.ActivityCustomerListBinding
import com.example.applolc_test.presentation.ui.detail.CustomerDetailActivity
import com.example.applolc_test.presentation.utils.ViewModelFactory
import com.example.applolc_test.presentation.utils.gone
import com.example.applolc_test.presentation.utils.visible

class CustomerListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomerListBinding
    private lateinit var adapter: CustomerAdapter

    private val viewModel: CustomerListViewModel by viewModels {
        ViewModelFactory((application as CustomerApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        observeViewModel()
        viewModel.loadCustomers()
    }

    override fun onResume() {
        super.onResume()
        // Refresh list to update visited status
        adapter.notifyDataSetChanged()
    }

    private fun setupUI() {
        adapter = CustomerAdapter(
            onItemClick = { customer ->
                navigateToDetail(customer)
            },
            isVisited = { customerId ->
                viewModel.isCustomerVisited(customerId)
            }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@CustomerListActivity)
            adapter = this@CustomerListActivity.adapter
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadCustomers()
        }
    }

    private fun observeViewModel() {
        viewModel.customers.observe(this) { customers ->
            adapter.submitList(customers)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.swipeRefresh.isRefreshing = isLoading
            if (isLoading) {
                binding.errorView.gone()
            }
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                binding.errorView.visible()
                binding.tvError.text = it
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            } ?: binding.errorView.gone()
        }
    }

    private fun navigateToDetail(customer: Customer) {
        val intent = Intent(this, CustomerDetailActivity::class.java).apply {
            putExtra("customer", customer)
        }
        startActivity(intent)
    }
}