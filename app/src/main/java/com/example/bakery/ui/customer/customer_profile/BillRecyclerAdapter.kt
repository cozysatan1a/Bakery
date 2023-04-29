package com.example.bakery.ui.customer.customer_profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bakery.data.model.Bill
import com.example.bakery.databinding.ItemBillBinding

class BillRecyclerAdapter(
    private val customerList: List<Bill?>,
    private val listener: OnClickListener
) :
    RecyclerView.Adapter<BillRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(val itemBinding: ItemBillBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(bill: Bill?) {
            var result = ""
            // Loop through each food order in the bill
            for (foodOrder in bill?.order ?: emptyList()) {
                // Get the name and quantity of the food order
                val name = foodOrder.food?.name ?: "Unknown"
                val quantity = foodOrder.quantity ?: 0
                // Append the name and quantity to the result with a comma separator
                result += "$name: $quantity, "
            }
            // Remove the last comma and space from the result
            result = result.dropLast(2)
            itemBinding.tvCode.text = result
            itemBinding.tvPrice.text = bill?.price
            itemBinding.tvStatus.text =
                if (bill?.completed == true) "Đã hoàn thành" else if (bill?.completed == false) "Đang tiến hành" else null
            itemBinding.tvCompletedTime.text = if (bill?.completed == true) bill.completeTime else "N/A"
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemBinding =
            ItemBillBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return customerList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val bill: Bill? = customerList[position]
        holder.bind(bill)
        holder.itemView.setOnClickListener {
            listener.onClick(Pair(bill, position))
        }

    }

    class OnClickListener(val clickListener: (Pair<Bill?, Int>) -> Unit) {
        fun onClick(callback : Pair<Bill?, Int>) = clickListener(callback)
    }
}
