package com.example.applolc_test.presentation.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.applolc_test.CustomerApplication
import com.example.applolc_test.data.model.Customer
import com.example.applolc_test.databinding.ActivityCustomerDetailBinding
import com.example.applolc_test.presentation.utils.ViewModelFactory
import com.example.applolc_test.R

class CustomerDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomerDetailBinding
    private var customer: Customer? = null

    private val viewModel: CustomerDetailViewModel by viewModels {
        ViewModelFactory((application as CustomerApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customer = intent.getSerializableExtra("customer") as? Customer

        setupUI()
        observeViewModel()
        customer?.let {
            viewModel.checkVisitedStatus(it.id)
        }
    }

    private fun setupUI() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        customer?.let { cust ->
            binding.apply {
                tvName.text = cust.name
                tvPhone.text = cust.phone
                tvEmail.text = cust.email
                tvAddress.text = cust.address
                tvCompany.text = cust.company
                tvWebsite.text = cust.website

                btnMarkVisited.setOnClickListener {
                    viewModel.markAsVisited(cust.id)
                }
            }
        }
    }

    private fun observeViewModel() {
        viewModel.isVisited.observe(this) { isVisited ->
            binding.btnMarkVisited.apply {
                isEnabled = !isVisited
                text = if (isVisited) "Already Visited" else "Mark as Visited"
                setBackgroundColor(
                    getColor(if (isVisited) R.color.visited_color else R.color.primary_color)
                )
            }
        }
    }

}
