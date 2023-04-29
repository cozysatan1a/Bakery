package com.example.bakery.ui.branch.branch_management

import androidx.lifecycle.MutableLiveData
import com.example.bakery.data.model.Branch
import com.example.bakery.ui.base.BaseViewModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class BranchManagementViewModel : BaseViewModel() {
    private val fStore : FirebaseFirestore = FirebaseFirestore.getInstance()
    val branches = MutableLiveData<MutableList<Branch>>(null)


    private fun getAllFoods(callback: (MutableList<Branch>) -> Unit) {
        fStore.collection("Branches").get().addOnSuccessListener { result->
            val branchList = mutableListOf<Branch>()
            for (document in result) {
                val branches = document.toObject(Branch::class.java)
                branchList.add(branches)
            }
            callback(branchList)
        }
    }

    fun loadData() {
        getAllFoods(branches::postValue)
    }
}