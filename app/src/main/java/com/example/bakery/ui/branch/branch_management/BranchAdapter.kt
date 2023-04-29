package com.example.bakery.ui.branch.branch_management

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bakery.data.model.Branch
import com.example.bakery.databinding.ItemBranchBinding

class BranchAdapter(
    private val branchList: MutableList<Branch>,
    private val listener: OnClickListener
) :
    RecyclerView.Adapter<BranchAdapter.MyViewHolder>() {

    class MyViewHolder(val itemBinding: ItemBranchBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(branch: Branch?, position: Int) {
            itemBinding.tvCode.text = (position + 1).toString()
            itemBinding.tvAddress.text = branch?.address
            itemBinding.tvPhone.text = branch?.phone
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemBinding =
            ItemBranchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return branchList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val branch: Branch? = branchList[position]
        holder.bind(branch, position)
        holder.itemView.setOnClickListener {
            listener.onClick(branch)
        }

    }

    class OnClickListener(val clickListener: (branch: Branch?) -> Unit) {
        fun onClick(branch: Branch?) = clickListener(branch)
    }
}