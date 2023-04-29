package com.example.bakery.ui.customer.edit_customer

import androidx.lifecycle.MutableLiveData
import com.example.bakery.data.model.FoodInfo
import com.example.bakery.ui.base.BaseViewModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class EditCustomerViewModel : BaseViewModel() {
    private val fStore: FirebaseFirestore = FirebaseFirestore.getInstance()


    fun updateCustomer(
        uid: String?,
        address: String,
        dob: String,
        gender: String,
        name: String,
        phone: String
    ) {
        val document = uid?.let { fStore.collection("Customers").document(it) }
        document?.apply {
            if (!address.isNullOrBlank()) {
                update("address", address)
            }
            if (!dob.isNullOrBlank()) {
                update("dob", dob)
            }
            if (!gender.isNullOrBlank()) {
                update("gender", gender)
            }
            if (!name.isNullOrBlank()) {
                update("name", name)
            }
            if (!phone.isNullOrBlank()) {
                update("phone", phone)
            }
        }

    }
}