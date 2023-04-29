package com.example.bakery.ui.bill.bill_detail

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.bakery.R
import com.example.bakery.data.model.Bill
import com.example.bakery.databinding.FragmentBillDetailBinding
import com.example.bakery.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BillDetailFragment : BaseFragment<FragmentBillDetailBinding, BillDetailViewModel>(),
    BillDetailListener {
    override val viewModel: BillDetailViewModel by viewModels()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_bill_detail
    }

    private val args: com.example.bakery.ui.bill.bill_detail.BillDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var currentBill: Bill? = null
        binding.listener = this@BillDetailFragment
        viewModel.getCurrentBill(args.uid) {
            currentBill = args.position?.let { position -> it?.bill?.get(position.toInt()) }
            var result = ""
            for (foodOrder in currentBill?.order ?: emptyList()) {
                val name = foodOrder.food?.name ?: "Unknown"
                val quantity = foodOrder.quantity ?: 0
                result += "$name: $quantity \n "
            }
            result = result.dropLast(2)
            binding.apply {
                tvFood.text = result
                tvPrice.text = currentBill?.price
                btnEdit.isVisible = currentBill?.completed == false
                tvStatus.text =
                    if (currentBill?.completed == true) "Đã hoàn thành" else if (currentBill?.completed == false) "Đang tiến hành" else "Unknown"
            }
        }
    }

    override fun onBackPressed() {
        activity?.onBackPressed()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMarkAsCompeleted() {
        viewModel.markAsCompleted(args.uid, args.position?.toInt()) {
            onBackPressed()
        }
    }

    override fun onDeleteBill() {
        viewModel.deleteBill(args.uid, args.position?.toInt()) {
            onBackPressed()
        }
    }
}