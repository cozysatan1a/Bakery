package com.example.bakery.ui.bill.create_bill

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bakery.R
import com.example.bakery.data.model.Bill
import com.example.bakery.data.model.FoodInfo
import com.example.bakery.data.model.FoodOrder
import com.example.bakery.databinding.FragmentCreateBillBinding
import com.example.bakery.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateBillFragment : BaseFragment<FragmentCreateBillBinding, CreateBillViewModel>(),
    CreateBillListener {
    override val viewModel: CreateBillViewModel by viewModels()

    private val args: CreateBillFragmentArgs by navArgs()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_create_bill
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var items = mutableListOf(1)

        binding.apply {
            listener = this@CreateBillFragment
            rvFoodOrder.layoutManager = LinearLayoutManager(activity)
            val adapter = context?.let { CreateBillAdapter(items, it) }
            rvFoodOrder.adapter = adapter

            btnAdd.setOnClickListener {
                items.add(1)
                (rvFoodOrder.adapter as RecyclerView.Adapter).notifyItemInserted(items.size - 1)
            }
            btnRemove.setOnClickListener {
                if (items.size > 1) {
                    items.removeLast()
                    (rvFoodOrder.adapter as RecyclerView.Adapter).notifyDataSetChanged()
                }
            }
            btnCreateBill.setOnClickListener {
                val data = adapter?.getData()
                val foodOrder = mutableListOf<FoodOrder>()
                data?.forEach {
                    foodOrder.add(
                        FoodOrder(
                            food = FoodInfo(
                                name = it.first?.name,
                                price = it.first?.price
                            ),
                            quantity = it.second
                        )
                    )
                }
                var sum = 0
                // Loop through each food order in the bill
                for (foodOrder in foodOrder) {
                    val price = foodOrder.food?.price?.toInt() ?: 0
                    val quantity = foodOrder.quantity ?: 0
                    sum += price * quantity
                }
                val bill = Bill(
                    order = foodOrder,
                    price = sum.toString(),
                    completed = false
                )

                viewModel.addBillToUser(args.uid, bill) {
                    onBackPressed()
                }
            }
        }

    }

    override fun onBackPressed() {
        activity?.onBackPressed()
    }
}