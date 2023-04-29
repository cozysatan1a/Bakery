package com.example.bakery.ui.user.user_profile

import com.example.bakery.data.model.Branch
import com.example.bakery.data.model.User
import com.example.bakery.ui.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class UserProfileViewModel : BaseViewModel() {
    private val fStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    fun getCurrentUserFirebase(uid: String?, callback: (User?) -> Unit) {
        val document = uid?.let { fStore.collection("Users").document(it) }
        document?.get()?.addOnSuccessListener {
            val userInfo = it.toObject(User::class.java)
            callback.invoke(userInfo)
        }
    }

    fun deleteUser(uid: String?, onSuccess: () -> Unit) {
        val document = uid?.let { fStore.collection("Users").document(it) }
        document?.delete()?.addOnSuccessListener {
            onSuccess.invoke()
        }
    }

    fun getCurrentBranchFirebase(uid: String?, callback: (Branch?) -> Unit) {
        val document = uid?.let { fStore.collection("Branches").document(it) }
        document?.get()?.addOnSuccessListener {
            val branchInfo = it.toObject(Branch::class.java)
            callback.invoke(branchInfo)
        }
    }
}