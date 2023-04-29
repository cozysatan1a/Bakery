package com.example.bakery.ui.food.food_management

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bakery.data.model.FoodInfo
import com.example.bakery.databinding.ItemFoodBinding

class FoodAdapter(
    private val foodList: MutableList<FoodInfo>,
    private val listener: OnClickListener
) :
    RecyclerView.Adapter<FoodAdapter.MyViewHolder>() {

    class MyViewHolder(val itemBinding: ItemFoodBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(food: FoodInfo?) {
            itemBinding.tvName.text = food?.name
            itemBinding.tvPrice.text = food?.price
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemBinding =
            ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val food: FoodInfo? = foodList[position]
        holder.bind(food)
        holder.itemView.setOnClickListener {
            listener.onClick(food)
        }

    }

    class OnClickListener(val clickListener: (food: FoodInfo?) -> Unit) {
        fun onClick(food: FoodInfo?) = clickListener(food)
    }
}