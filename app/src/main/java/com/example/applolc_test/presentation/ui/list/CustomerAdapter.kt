package com.example.applolc_test.presentation.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.applolc_test.data.model.Customer
import com.google.android.material.chip.Chip
import com.example.applolc_test.R

class CustomerAdapter(
    private val onItemClick: (Customer) -> Unit,
    private val isVisited: (Int) -> Boolean
) : RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>() {

    private var customers = listOf<Customer>()

    fun submitList(list: List<Customer>) {
        customers = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_customer, parent, false)
        return CustomerViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        holder.bind(customers[position])
    }

    override fun getItemCount() = customers.size

    inner class CustomerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardView: CardView = itemView.findViewById(R.id.cardView)
        private val nameText: TextView = itemView.findViewById(R.id.tvCustomerName)
        private val phoneText: TextView = itemView.findViewById(R.id.tvCustomerPhone)
        private val addressText: TextView = itemView.findViewById(R.id.tvCustomerAddress)
        private val visitedChip: Chip = itemView.findViewById(R.id.chipVisited)

        fun bind(customer: Customer) {
            nameText.text = customer.name
            phoneText.text = customer.phone
            addressText.text = customer.address

            val visited = isVisited(customer.id)
            visitedChip.visibility = if (visited) View.VISIBLE else View.GONE

            cardView.setOnClickListener {
                onItemClick(customer)
            }
        }
    }
}
