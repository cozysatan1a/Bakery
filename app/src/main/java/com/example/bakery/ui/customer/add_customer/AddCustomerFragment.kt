package com.example.bakery.ui.customer.add_customer

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import com.example.bakery.R
import com.example.bakery.data.model.Customer
import com.example.bakery.databinding.FragmentAddCustomerBinding
import com.example.bakery.ui.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCustomerFragment : BaseFragment<FragmentAddCustomerBinding, AddCustomerViewModel>(),
    AddCustomerListener {
    override val viewModel: AddCustomerViewModel by viewModels()
    private val fStore = FirebaseFirestore.getInstance()


    override fun getLayoutRes(): Int {
        return R.layout.fragment_add_customer
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listener = this@AddCustomerFragment
    }

    override fun onBackPressed() {
        activity?.onBackPressed()
    }

    override fun onClickSignUp() {
        val documentId : String = fStore.collection("Customers").document().id
        val documentReference: DocumentReference = fStore.collection("Customers").document(documentId)
        val selectedGender: RadioButton =
            binding.root.findViewById(binding.rgGender.checkedRadioButtonId)

        val customer = Customer(
            name = binding.edtName.text.toString(),
            gender = selectedGender.text.toString(),
            address = binding.edtAddress.text.toString(),
            dob = binding.edtDob.text.toString(),
            phone = binding.edtPhone.text.toString(),
            id = documentId,
            bill = null
        )
        documentReference.set(customer)
        Snackbar.make(
            binding.btnLogin,
            "Tạo khách hàng thành công",
            Snackbar.LENGTH_SHORT
        ).show()
    }


}