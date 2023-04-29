package com.example.bakery.ui.customer.customer_management

import com.example.bakery.data.model.Customer
import com.example.bakery.data.model.User
import com.example.bakery.ui.base.BaseViewModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class CustomerManagementViewModel : BaseViewModel() {
    private val fStore : FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getAllCustomer(callback: (List<Customer?>) -> Unit) {
        fStore.collection("Customers").get().addOnSuccessListener { result->
            val customerList = mutableListOf<Customer?>()
            for (document in result) {
                val customers = document.toObject(Customer::class.java)
                customerList.add(customers)
            }
            callback.invoke(customerList)
        }.addOnFailureListener {
        }
    }
}