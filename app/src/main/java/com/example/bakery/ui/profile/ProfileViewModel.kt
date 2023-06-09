package com.example.bakery.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.work.ListenableWorker.Result.Success
import com.example.bakery.data.model.User
import com.example.bakery.ui.base.BaseViewModel
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class ProfileViewModel : BaseViewModel() {
    private val fStore : FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getCurrentUserFirebase(uid: String?,callback: (User?) -> Unit) {
        val document = uid?.let { fStore.collection("Users").document(it) }
        document?.get()?.addOnSuccessListener {
            val userInfo = it.toObject(User::class.java)
            callback.invoke(userInfo)
        }
    }

    fun updatePassword(email: String, password: String, newPassword : String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val credential : AuthCredential = EmailAuthProvider.getCredential(email, password)
        val user = FirebaseAuth.getInstance().currentUser
        user?.reauthenticate(credential)?.addOnSuccessListener {
            user.updatePassword(newPassword)
                .addOnSuccessListener {
                    onSuccess.invoke()
                }.addOnFailureListener {
                    onFailure.invoke(it)
                }
        }?.addOnFailureListener {
            onFailure.invoke(it)
        }

    }
}