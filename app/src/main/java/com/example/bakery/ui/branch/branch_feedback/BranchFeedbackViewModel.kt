package com.example.bakery.ui.branch.branch_feedback

import com.example.bakery.data.model.Branch
import com.example.bakery.data.model.Feedback
import com.example.bakery.ui.base.BaseViewModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class BranchFeedbackViewModel: BaseViewModel() {
    private val fStore : FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getAllFeedback(uid: String?, onSuccess:(List<Feedback>) -> Unit) {
        val branchRef = uid?.let { fStore.collection("Branches").document(it) }

        branchRef?.get()
            ?.addOnSuccessListener { document ->
                if (document != null) {
                    val branch = document.toObject(Branch::class.java)
                    val feedbackList = branch?.feedback
                    if (feedbackList != null) {
                        onSuccess.invoke(feedbackList)
                    }
                }
            }

    }
    fun updateBranch(uid: String?, name: String?, feedback: String, onSuccess:() -> Unit) {
        val data = Feedback(
            if (name.isNullOrBlank()) "Ẩn danh" else name,
            feedback
        )
        val document = uid?.let { fStore.collection("Branches").document(it) }
        document?.update("feedback", FieldValue.arrayUnion(data))?.addOnSuccessListener {
            onSuccess.invoke()
        }
    }
}