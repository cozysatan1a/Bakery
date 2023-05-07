package com.example.bakery.ui.branch.branch_data

import android.os.Bundle
import android.view.View
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
                    tvIncome.text = totalIncome.toString()
                    rvUserList.layoutManager = LinearLayoutManager(activity)
                    rvUserList.adapter = BranchDataAdapter(billList)
                }
            }
        }
    }

    override fun onBackPressed() {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }
}