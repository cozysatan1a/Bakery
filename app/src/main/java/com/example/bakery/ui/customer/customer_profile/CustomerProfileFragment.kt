package com.example.bakery.ui.customer.customer_profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bakery.R
import com.example.bakery.databinding.FragmentCustomerProfileBinding
import com.example.bakery.ui.base.BaseFragment
import com.google.android.material.snackbar.Snackbar

class CustomerProfileFragment :
    BaseFragment<FragmentCustomerProfileBinding, CustomerProfileViewModel>(),
    CustomerProfileListener {
    override val viewModel: CustomerProfileViewModel by viewModels()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_customer_profile
    }

    private val args: com.example.bakery.ui.customer.customer_profile.CustomerProfileFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            listener = this@CustomerProfileFragment
            rvBill.layoutManager = LinearLayoutManager(activity)
        }
        viewModel.getCurrentCustomerFirebase(args.uid) {
            binding.apply {
                tvAddress.text = it?.address
                tvPhone.text = it?.phone
                tvDob.text = it?.dob
                tvName.text = it?.name
                tvGender.text = it?.gender
                rvBill.adapter = it?.bill?.let { bill ->
                    BillRecyclerAdapter(bill, BillRecyclerAdapter.OnClickListener {
                        val action =
                            com.example.bakery.ui.customer.customer_profile.CustomerProfileFragmentDirections.goToBillDetail()
                                .setPosition(it.second.toString()).setUid(args.uid)
                        findNavController().navigate(action)
                    })
                }
            }
        }
    }

    override fun onBackPressed() {
        activity?.onBackPressed()
    }

    override fun onClickDeleteCustomer() {
        viewModel.deleteCustomer(args.uid) {
            activity?.onBackPressed()
            Snackbar.make(
                binding.btnDelete,
                "Xóa thông tin khách hàng thành công",
                Snackbar.LENGTH_SHORT
            )
        }
    }

    override fun onClickEditInfo() {
        val action =
            com.example.bakery.ui.customer.customer_profile.CustomerProfileFragmentDirections.goToEditCustomer()
                .setUid(args.uid)
        findNavController().navigate(action)
    }

    override fun onCreateNewBill() {
        val action =
            com.example.bakery.ui.customer.customer_profile.CustomerProfileFragmentDirections.goToCreateBill()
                .setUid(args.uid)
        findNavController().navigate(action)
    }
}