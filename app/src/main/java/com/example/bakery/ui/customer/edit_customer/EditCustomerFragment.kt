package com.example.bakery.ui.customer.edit_customer

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.bakery.R
import com.example.bakery.databinding.FragmentEditCustomerBinding
import com.example.bakery.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditCustomerFragment : BaseFragment<FragmentEditCustomerBinding, EditCustomerViewModel>(),
    EditCustomerListener {
    override val viewModel: EditCustomerViewModel by viewModels()
    private val args : EditCustomerFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listener = this@EditCustomerFragment
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_edit_customer
    }

    override fun onBackPressed() {
        activity?.onBackPressed()
    }

    override fun onEditCustomer() {
        binding.apply {
            val selectedGender: RadioButton =
                binding.root.findViewById(binding.rgGender.checkedRadioButtonId)
            viewModel.updateCustomer(
                args.uid,
                address = edtAddress.text.toString(),
                dob = edtDob.text.toString(),
                gender = selectedGender.text.toString(),
                name = edtName.text.toString(),
                phone = edtPhone.text.toString()
            )
            onBackPressed()
        }
    }
}