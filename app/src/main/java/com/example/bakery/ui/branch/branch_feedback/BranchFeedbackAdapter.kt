package com.example.bakery.ui.branch.branch_feedback

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bakery.data.model.Feedback
import com.example.bakery.databinding.ItemFeedbackBinding

class BranchFeedbackAdapter(
    private val dataList: MutableList<Feedback>
) :
    RecyclerView.Adapter<BranchFeedbackAdapter.MyViewHolder>() {

    class MyViewHolder(val itemBinding: ItemFeedbackBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(feedback: Feedback, position: Int) {
            itemBinding.tvNumber.text = (position + 1).toString()
            itemBinding.tvName.text = feedback.customer
            itemBinding.tvFeedback.text = feedback.feedback
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemBinding =
            ItemFeedbackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val feedback: Feedback = dataList[position]
        holder.bind(feedback, position)
    }
}