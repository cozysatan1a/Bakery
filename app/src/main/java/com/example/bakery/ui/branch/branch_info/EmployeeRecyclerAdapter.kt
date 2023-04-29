package com.example.bakery.ui.branch.branch_info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bakery.data.model.User
import com.example.bakery.databinding.ItemUserInBranchBinding

class EmployeeRecyclerAdapter(
    private val employeeList: List<User?>,
    private val listener: OnClickListener
) :
    RecyclerView.Adapter<EmployeeRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(val itemBinding: ItemUserInBranchBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(employee: User?, position: Int) {
            itemBinding.tvNumber.text = position.toString()
            itemBinding.tvCode.text = employee?.code
            itemBinding.tvName.text = employee?.name
            itemBinding.tvGender.text = employee?.gender
            itemBinding.tvEmail.text = employee?.email
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemBinding =
            ItemUserInBranchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return employeeList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val employee: User? = employeeList[position]
        holder.bind(employee, position)
        holder.itemView.setOnClickListener {
            listener.onClick(employee)
        }

    }

    class OnClickListener(val clickListener: (employee: User?) -> Unit) {
        fun onClick(employee: User?) = clickListener(employee)
    }
}