package com.example.bakery.ui.bill.bill_detail

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.bakery.data.model.Customer
import com.example.bakery.ui.base.BaseViewModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.LocalDateTime

@HiltViewModel
class BillDetailViewModel : BaseViewModel() {
    private val fStore : FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getCurrentBill(uid: String?, callback: (Customer?) -> Unit) {
        val document = uid?.let { fStore.collection("Customers").document(it) }
        document?.get()?.addOnSuccessListener {
            val customerInfo = it.toObject(Customer::class.java)
            callback.invoke(customerInfo)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun markAsCompleted(uid: String?, position: Int?, callback: () -> Unit) {
        val document = uid?.let { fStore.collection("Customers").document(it) }
        document?.get()?.addOnSuccessListener {
            val customerInfo = it.toObject(Customer::class.java)
            val currentBill = customerInfo?.bill?.get(position!!)
            document.update("bill", FieldValue.arrayRemove(currentBill))
            currentBill?.completed = true
            currentBill?.completeTime = LocalDate.now().toString()
            document.update("bill", FieldValue.arrayUnion(currentBill)).addOnSuccessListener {
                callback.invoke()
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