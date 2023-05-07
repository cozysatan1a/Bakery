package com.example.bakery.ui.customer.customer_management

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bakery.data.model.Customer
import com.example.bakery.databinding.ItemCustomerBinding

class CustomerAdapter(
    private val customerList: List<Customer?>,
    private val listener: OnClickListener
) :
    RecyclerView.Adapter<CustomerAdapter.MyViewHolder>() {

    class MyViewHolder(val itemBinding: ItemCustomerBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(customer: Customer?, position: Int) {
            itemBinding.tvName.text = customer?.name
            itemBinding.tvCode.text = (position+1).toString()
            itemBinding.tvAddress.text = customer?.address
            itemBinding.tvGender.text = customer?.gender
            itemBinding.tvPhone.text = customer?.phone
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemBinding =
            ItemCustomerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return customerList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val customer: Customer? = customerList[position]
        holder.bind(customer, position)
        holder.itemView.setOnClickListener {
            listener.onClick(customer)
        }

    }

    class OnClickListener(val clickListener: (customer: Customer?) -> Unit) {
        fun onClick(customer: Customer?) = clickListener(customer)
    }
}