package com.example.bakery.ui.customer.customer_management

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bakery.R
import com.example.bakery.databinding.FragmentCustomerManagementBinding
import com.example.bakery.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerManagementFragment :
    BaseFragment<FragmentCustomerManagementBinding, CustomerManagementViewModel>(),
    CustomerManagementListener {
    override val viewModel: CustomerManagementViewModel by viewModels()
    override fun getLayoutRes(): Int {
        return R.layout.fragment_customer_management
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listener = this@CustomerManagementFragment
        binding.apply {
            rvCustomerList.layoutManager = LinearLayoutManager(activity)
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
                        val list = it.filter {
                            query?.let { query -> it?.name?.contains(query) } == true
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
}