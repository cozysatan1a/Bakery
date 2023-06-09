package com.example.bakery.ui.branch.branch_management

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bakery.R
import com.example.bakery.databinding.FragmentStorageManagementBinding
import com.example.bakery.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BranchManagementFragment :
    BaseFragment<FragmentStorageManagementBinding, BranchManagementViewModel>(),
    BranchManagementListener {
    override val viewModel: BranchManagementViewModel by viewModels()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_storage_management
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadData()
        binding.apply {
            listener = this@BranchManagementFragment
            rvBranchList.layoutManager = LinearLayoutManager(activity)
            viewModel.branches.observe(viewLifecycleOwner) {
                it?.let {
                    rvBranchList.adapter =
                        BranchAdapter(it, BranchAdapter.OnClickListener { branch ->
                            val action = BranchManagementFragmentDirections.goToBranchInfo()
                                .setId(branch?.id)
                            findNavController().navigate(action)
                        })
                    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                        androidx.appcompat.widget.SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            val list = it.filter {
                                query?.let { query ->
                                    it.address?.lowercase()?.contains(query.lowercase())
                                } == true
                            }
                            binding.rvBranchList.adapter =
                                BranchAdapter(
                                    list.toMutableList(),
                                    BranchAdapter.OnClickListener { branch ->
                                        val action =
                                            BranchManagementFragmentDirections.goToBranchInfo()
                                                .setId(branch?.id)
                                        findNavController().navigate(action)
                                    })
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            binding.rvBranchList.adapter =
                                BranchAdapter(it, BranchAdapter.OnClickListener { branch ->
                                    val action = BranchManagementFragmentDirections.goToBranchInfo()
                                        .setId(branch?.id)
                                    findNavController().navigate(action)
                                })
                            return false
                        }
                    })
                }

            }
        }
    }

    override fun onBackPressed() {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }

    override fun onCreateNewBranch() {
        findNavController().navigate(R.id.goToCreateBranch)
    }

    override fun onViewChainData() {
        findNavController().navigate(R.id.goToChainData)
    }
}