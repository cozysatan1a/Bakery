package com.example.bakery.ui.branch.branch_data

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bakery.R
import com.example.bakery.data.model.Branch
import com.example.bakery.databinding.FragmentBranchDataBinding
import com.example.bakery.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BranchDataFragment : BaseFragment<FragmentBranchDataBinding, BranchDataViewModel>(),
    BranchDataListener {
    override val viewModel: BranchDataViewModel by viewModels()
    private val args: BranchDataFragmentArgs by navArgs()
    override fun getLayoutRes(): Int {
        return R.layout.fragment_branch_data
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            listener = this@BranchDataFragment
            args.id?.let {
                viewModel.getData(it) { monthList, totalIncome, billList ->
                    val adapter: ArrayAdapter<*> = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        monthList
                    )
                    llHeader.isVisible = (billList.size > 0)
                    spinner.isVisible = (monthList.size > 0)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter
                    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            val selectedMonthYear = monthList[position]
                            val foodRevenueForSelectedMonth = billList.filter { revenue -> revenue.monthYear == selectedMonthYear }
                            rvUserList.layoutManager = LinearLayoutManager(activity)
                            rvUserList.adapter = BranchDataAdapter(foodRevenueForSelectedMonth.toMutableList())
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                        }

                    }
                    tvIncome.text = totalIncome.toString()

                }
            }
        }
    }

    override fun onBackPressed() {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }
}