package com.example.bakery.ui.signup

import com.example.bakery.data.model.Branch
import com.example.bakery.ui.base.BaseViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class SignUpViewModel : BaseViewModel() {
    private val fStore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getAllBranches(callback: (MutableList<Branch>) -> Unit) {
        fStore.collection("Branches").get().addOnSuccessListener { result->
            val branchList = mutableListOf<Branch>()
            for (document in result) {
                val branches = document.toObject(Branch::class.java)
                branchList.add(branches)
            }
            callback(branchList)
        }
    }
}