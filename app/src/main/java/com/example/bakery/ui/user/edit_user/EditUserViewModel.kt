package com.example.bakery.ui.user.edit_user

import com.example.bakery.data.model.Branch
import com.example.bakery.data.model.User
import com.example.bakery.ui.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class EditUserViewModel : BaseViewModel() {
    private val fStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val fAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun updateCurrentUser(uid: String?, code: String, dob: String, gender: String, name: String, branch: String, isAdmin: String, isBranchMaster: Boolean) {
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
            if (!isAdmin.isNullOrBlank()) {
                update("admin", isAdmin)
            }
            update("head", isBranchMaster)

        }
    }

    fun getCurrentUserFirebase(uid: String?,callback: (User?) -> Unit) {
        val document = uid?.let { fStore.collection("Users").document(it) }
        document?.get()?.addOnSuccessListener {
            val userInfo = it.toObject(User::class.java)
            callback.invoke(userInfo)
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

    fun getUserBranch(uid: String?, callback: (Branch?) -> Unit) {
        val document = uid?.let { fStore.collection("Branches").document(it) }
        document?.get()?.addOnSuccessListener {
            val branch = it.toObject(Branch::class.java)
            callback.invoke(branch)
        }
    }
}