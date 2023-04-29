package com.example.bakery.ui.profile

import androidx.lifecycle.MutableLiveData
import com.example.bakery.data.model.User
import com.example.bakery.ui.base.BaseViewModel
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
}