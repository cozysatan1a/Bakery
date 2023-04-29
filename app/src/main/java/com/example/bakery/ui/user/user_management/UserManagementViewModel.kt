package com.example.bakery.ui.user.user_management

import androidx.lifecycle.MutableLiveData
import com.example.bakery.data.model.User
import com.example.bakery.ui.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class UserManagementViewModel : BaseViewModel() {
    private val fStore : FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getAllUser(callback: (List<User?>) -> Unit) {
        fStore.collection("Users").get().addOnSuccessListener { result->
            val userList = mutableListOf<User?>()
            for (document in result) {
                val users = document.toObject(User::class.java)
                userList.add(users)
            }
            callback.invoke(userList)
        }
    }
}