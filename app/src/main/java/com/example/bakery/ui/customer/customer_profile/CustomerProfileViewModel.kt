package com.example.bakery.ui.customer.customer_profile

import com.example.bakery.data.model.Customer
import com.example.bakery.data.model.User
import com.example.bakery.ui.base.BaseViewModel
import com.google.firebase.firestore.FirebaseFirestore

class CustomerProfileViewModel : BaseViewModel() {
    private val fStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    fun getCurrentCustomerFirebase(uid: String?, callback: (Customer?) -> Unit) {
        val document = uid?.let { fStore.collection("Customers").document(it) }
        document?.get()?.addOnSuccessListener {
            val customerInfo = it.toObject(Customer::class.java)
            callback.invoke(customerInfo)
        }
    }

    fun deleteCustomer(uid: String?, onSuccess: () -> Unit) {
        val document = uid?.let { fStore.collection("Customers").document(it) }
        document?.delete()?.addOnSuccessListener {
            onSuccess.invoke()
        }
    }
}