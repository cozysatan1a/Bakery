package com.example.bakery.ui.home

import androidx.lifecycle.MutableLiveData
import com.example.bakery.data.model.User
import com.example.bakery.ui.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class HomeViewModel : BaseViewModel() {
    val fAuth : FirebaseAuth = FirebaseAuth.getInstance()
    val fStore : FirebaseFirestore = FirebaseFirestore.getInstance()
    var currentUser = MutableLiveData<User?>()

    fun getCurrentUserFirebase(callback: (User?) -> Unit) {
        val currentUid = fAuth.uid
        val document = currentUid?.let { fStore.collection("Users").document(it) }
        document?.get()?.addOnSuccessListener {
            val userInfo = it.toObject(User::class.java)
            callback.invoke(userInfo)
        }
    }
    
}