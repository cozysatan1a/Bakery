package com.example.bakery.ui.branch.chain_data

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bakery.R
import com.example.bakery.databinding.FragmentChainDataBinding
import com.example.bakery.ui.base.BaseFragment
import com.example.bakery.ui.branch.branch_data.BranchDataAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChainDataFragment : BaseFragment<FragmentChainDataBinding, ChainDataViewModel>(), ChainDataListener {
    override val viewModel: ChainDataViewModel by viewModels()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_chain_data
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            listener = this@ChainDataFragment
            viewModel.getData() { monthList, totalIncome, billList ->
                val adapter: ArrayAdapter<*> = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    monthList
                )
                llHeader.isVisible = (billList.size > 0)
                spinner.isVisible = (monthList.size > 0)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
                tvIncome.text = totalIncome.toString()
                rvUserList.layoutManager = LinearLayoutManager(activity)
                rvUserList.adapter = BranchDataAdapter(billList)
            }
        }
    }

    override fun onBackPressed() {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }
}