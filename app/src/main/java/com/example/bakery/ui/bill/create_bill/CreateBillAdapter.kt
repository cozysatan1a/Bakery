package com.example.bakery.ui.bill.create_bill

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.bakery.data.model.FoodInfo
import com.example.bakery.data.model.foodList
import com.example.bakery.data.model.loadFoodList
import com.example.bakery.databinding.ItemFoodOrderBinding
import dagger.hilt.android.qualifiers.ApplicationContext

class CreateBillAdapter(
    private val itemList: List<Int>,
    @ApplicationContext private val context: Context
) :
    RecyclerView.Adapter<CreateBillAdapter.MyViewHolder>() {

    private val datas =  mutableListOf<Pair<FoodInfo?, Int>>()

    class MyViewHolder(val itemBinding: ItemFoodOrderBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemBinding =
            ItemFoodOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val choices = foodList
        val adapter: ArrayAdapter<*> = ArrayAdapter(
            context,
            android.R.layout.simple_spinner_item,
            choices
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.itemBinding.spinnerFood.adapter = adapter
        holder.itemBinding.apply {
            btnConfirm.setOnClickListener {
                datas.add(
                    Pair(
                        spinnerFood.selectedItem as FoodInfo,
                        edtQuantity.text.toString().toInt()
                    )
                )
                btnConfirm.isVisible = false
            }
        }
    }

    fun getData(): MutableList<Pair<FoodInfo?, Int>> {
        return datas
    }
}