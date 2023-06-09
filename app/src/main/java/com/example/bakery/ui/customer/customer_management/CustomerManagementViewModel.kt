package com.example.bakery.ui.customer.customer_management

import androidx.lifecycle.MutableLiveData
import com.example.bakery.data.model.Branch
import com.example.bakery.data.model.Customer
import com.example.bakery.data.model.Feedback
import com.example.bakery.data.model.User
import com.example.bakery.ui.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
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

    val fAuth : FirebaseAuth = FirebaseAuth.getInstance()
    var currentUser = MutableLiveData<User?>()

    fun getCurrentUserFirebase(callback: (User?) -> Unit) {
        val currentUid = fAuth.uid
        val document = currentUid?.let { fStore.collection("Users").document(it) }
        document?.get()?.addOnSuccessListener {
            val userInfo = it.toObject(User::class.java)
            callback.invoke(userInfo)
        }
    }

    fun updateBranch(uid: String?, name: String?, feedback: String, onSuccess:() -> Unit) {
        val data = Feedback(
            if (name.isNullOrBlank()) "Ẩn danh" else name,
            feedback
        )
        val document = uid?.let { fStore.collection("Branches").document(it) }
        document?.update("feedback", FieldValue.arrayUnion(data))?.addOnSuccessListener {
            onSuccess.invoke()
        }
    }
}