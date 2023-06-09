package com.example.bakery.ui.customer.customer_management

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bakery.R
import com.example.bakery.databinding.FragmentCustomerManagementBinding
import com.example.bakery.ui.base.BaseFragment
import com.example.bakery.ui.branch.branch_feedback.AddFeedbackDialogFragment
import com.example.bakery.ui.branch.branch_feedback.BranchFeedbackAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerManagementFragment :
    BaseFragment<FragmentCustomerManagementBinding, CustomerManagementViewModel>(),
    CustomerManagementListener {
    override val viewModel: CustomerManagementViewModel by viewModels()

    private var dialogFeedback = AddFeedbackDialogFragment()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_customer_management
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listener = this@CustomerManagementFragment
        binding.apply {
            rvCustomerList.layoutManager = LinearLayoutManager(activity)
        }
        viewModel.getCurrentUserFirebase {
            if (it?.admin == "1" || it?.head == true) {
                binding.btnFeedback.isVisible = false
            }
        }
        viewModel.getAllCustomer {
            binding.rvCustomerList.adapter =
                CustomerAdapter(it, CustomerAdapter.OnClickListener { customer ->
                    val action = CustomerManagementFragmentDirections.openCustomerProfile()
                        .setUid(customer?.id)
                    findNavController().navigate(action)
                })
            binding.apply {
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                    androidx.appcompat.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        val list = it.filter { customer ->
                            query?.let { query -> customer?.name?.lowercase()?.contains(query.lowercase()) } == true
                        }
                        binding.rvCustomerList.adapter =
                            CustomerAdapter(list, CustomerAdapter.OnClickListener { customer ->
                                val action =
                                    com.example.bakery.ui.customer.customer_management.CustomerManagementFragmentDirections.openCustomerProfile()
                                        .setUid(customer?.id)
                                findNavController().navigate(action)
                            })
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        binding.rvCustomerList.adapter =
                            CustomerAdapter(it, CustomerAdapter.OnClickListener { customer ->
                                val action =
                                    com.example.bakery.ui.customer.customer_management.CustomerManagementFragmentDirections.openCustomerProfile()
                                        .setUid(customer?.id)
                                findNavController().navigate(action)
                            })
                        return false
                    }
                })
            }
        }

    }


    override fun addCustomer() {
        findNavController().navigate(R.id.addCustomer)
    }

    override fun onBackPressed() {
        activity?.onBackPressed()
    }

    override fun onAddFeedback() {
        dialogFeedback.apply dialog@{
            this@CustomerManagementFragment.childFragmentManager.apply manager@{
                this@dialog.onAccept = { name, feedback ->
                    if (feedback.isNullOrBlank()) {
                        Toast.makeText(
                            requireContext(),
                            "Feedback không được để trống",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        viewModel.getCurrentUserFirebase {
                            viewModel.updateBranch(it?.branch, name, feedback) {
                                this@dialog.dismiss()
                                Toast.makeText(requireContext(), "Thêm thành công", Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                }
                this@dialog.onCancel = {
                    this@dialog.dismiss()
                }
                show(
                    this@manager,
                    isShow = true
                )
            }
        }
    }
}