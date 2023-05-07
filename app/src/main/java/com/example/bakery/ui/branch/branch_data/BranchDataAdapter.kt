package com.example.bakery.ui.branch.branch_data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bakery.databinding.ItemIncomeDataBinding

class BranchDataAdapter(
    private val dataList: MutableList<FoodRevenue>,) :
    RecyclerView.Adapter<BranchDataAdapter.MyViewHolder>() {

    class MyViewHolder(val itemBinding: ItemIncomeDataBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(revenue: FoodRevenue, position: Int) {
            itemBinding.tvNumber.text = (position + 1).toString()
            itemBinding.tvName.text = revenue.foodName
            itemBinding.tvCode.text = revenue.revenue.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemBinding =
            ItemIncomeDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val revenue: FoodRevenue = dataList[position]
        holder.bind(revenue, position)
    }
}