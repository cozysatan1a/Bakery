package com.example.bakery.ui.branch.edit_branch

import com.example.bakery.data.model.loadFoodList
import com.example.bakery.ui.base.BaseViewModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class EditBranchViewModel : BaseViewModel(){
    private val fStore : FirebaseFirestore = FirebaseFirestore.getInstance()
    fun updateBranch(uid: String?, address: String, phone: String) {
        val document = uid?.let { fStore.collection("Branches").document(it) }
        document?.apply {
            if (address.isNotBlank()) {
                update("address", address)
            }
            if (phone.isNotBlank()) {
                update("phone", phone)
            }
        }
    }
}