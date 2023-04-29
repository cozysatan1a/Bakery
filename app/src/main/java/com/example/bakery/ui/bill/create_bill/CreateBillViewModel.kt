package com.example.bakery.ui.bill.create_bill

import com.example.bakery.data.model.Bill
import com.example.bakery.ui.base.BaseViewModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class CreateBillViewModel : BaseViewModel() {
    val fStore = FirebaseFirestore.getInstance()

    fun addBillToUser(uid: String?, data: Bill, onSuccess: () -> Unit) {
        val document = uid?.let { fStore.collection("Customers").document(it) }
        document?.update("bill", FieldValue.arrayUnion(data))?.addOnSuccessListener {
            onSuccess.invoke()
        }
    }
}