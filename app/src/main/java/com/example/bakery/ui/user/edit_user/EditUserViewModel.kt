package com.example.bakery.ui.user.edit_user

import com.example.bakery.data.model.Branch
import com.example.bakery.ui.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class EditUserViewModel : BaseViewModel() {
    private val fStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val fAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun updateCurrentUser(uid: String?, code: String, dob: String, gender: String, name: String, branch: String) {
        val document = uid?.let { fStore.collection("Users").document(it) }
        document?.apply {
            if (!code.isNullOrBlank()) {
                update("code", code)
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
            if (!branch.isNullOrBlank()) {
                update("branch", branch)
            }
        }
    }

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