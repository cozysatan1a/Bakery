package com.example.bakery.ui.branch.create_branch

import com.example.bakery.data.model.Branch
import com.example.bakery.ui.base.BaseViewModel
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class CreateBranchViewModel : BaseViewModel() {
    private val fStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    fun createBranch(address: String, phone: String, onSuccess: () -> Unit) {
        val documentId: String = fStore.collection("Branches").document().id
        val documentReference: DocumentReference =
            fStore.collection("Branches").document(documentId)

        val branch = Branch(
            id = documentId,
            address = address,
            phone = phone
        )
        documentReference.set(branch).addOnSuccessListener {
            onSuccess.invoke()
        }
    }
}

