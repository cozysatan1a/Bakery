package com.example.bakery.ui.bill.bill_detail

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.bakery.data.model.Customer
import com.example.bakery.data.model.User
import com.example.bakery.ui.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate

@HiltViewModel
class BillDetailViewModel : BaseViewModel() {
    private val fStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val fAuth: FirebaseAuth = FirebaseAuth.getInstance()
    fun getCurrentBill(uid: String?, callback: (Customer?) -> Unit) {
        val document = uid?.let { fStore.collection("Customers").document(it) }
        document?.get()?.addOnSuccessListener {
            val customerInfo = it.toObject(Customer::class.java)
            callback.invoke(customerInfo)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun markAsCompleted(uid: String?, position: Int?, callback: () -> Unit) {
        val currentUserUId = fAuth.currentUser?.uid
        val currentUser =
            currentUserUId?.let { currentUser -> fStore.collection("Users").document(currentUser) }
        currentUser?.get()?.addOnSuccessListener {
            val userInfo = it.toObject(User::class.java)
            val document = uid?.let { fStore.collection("Customers").document(it) }
            document?.get()?.addOnSuccessListener {
                val customerInfo = it.toObject(Customer::class.java)
                val currentBill = customerInfo?.bill?.get(position!!)
                document.update("bill", FieldValue.arrayRemove(currentBill))
                currentBill?.completed = true
                currentBill?.completeTime = LocalDate.now().toString()
                currentBill?.branch = userInfo?.branch
                document.update("bill", FieldValue.arrayUnion(currentBill)).addOnSuccessListener {
                    callback.invoke()
                }
            }

        }
    }

    fun deleteBill(uid: String?, position: Int?, callback: () -> Unit) {
        val document = uid?.let { fStore.collection("Customers").document(it) }
        document?.get()?.addOnSuccessListener {
            val customerInfo = it.toObject(Customer::class.java)
            val currentBill = customerInfo?.bill?.get(position!!)
            document.update("bill", FieldValue.arrayRemove(currentBill)).addOnSuccessListener {
                callback.invoke()
            }
        }
    }
}