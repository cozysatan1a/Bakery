package com.example.bakery.ui.edit_profile

import com.example.bakery.ui.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class EditProfileViewModel : BaseViewModel() {
    private val fStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val fAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun updateCurrentUser(code: String, dob: String, gender: String, name: String) {
        val currentUserUId = fAuth.currentUser?.uid
        val document = currentUserUId?.let { fStore.collection("Users").document(it) }
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
        }
    }
}