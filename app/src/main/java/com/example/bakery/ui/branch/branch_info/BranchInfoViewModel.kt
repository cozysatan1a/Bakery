package com.example.bakery.ui.branch.branch_info

import com.example.bakery.data.model.Branch
import com.example.bakery.data.model.User
import com.example.bakery.ui.base.BaseViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class BranchInfoViewModel : BaseViewModel() {
    private val fStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    fun getCurrentBranchFirebase(uid: String?, callback: (Branch?) -> Unit) {
        val document = uid?.let { fStore.collection("Branches").document(it) }
        document?.get()?.addOnSuccessListener {
            val branchInfo = it.toObject(Branch::class.java)
            callback.invoke(branchInfo)
        }
    }

    fun deleteBranch(uid: String?, onSuccess: () -> Unit) {
        val document = uid?.let { fStore.collection("Branches").document(it) }
        document?.delete()?.addOnSuccessListener {
            onSuccess.invoke()
        }
    }

    fun getAllUsersInCurrentBranch(uid: String?, callback: (MutableList<User>) -> Unit, onFailure:() -> Unit) {
        fStore.collection("Users")
            .whereEqualTo("branch", uid)
            .get()
            .addOnSuccessListener { documents ->
                val userList = mutableListOf<User>()
                for (document in documents) {
                    val user = document.toObject(User::class.java)
                    userList.add(user)
                }
                callback.invoke(userList)
            }
            .addOnFailureListener { exception ->
                onFailure.invoke()
            }
    }
}