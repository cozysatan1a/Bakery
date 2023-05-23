package com.example.bakery.ui.branch.branch_feedback

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.bakery.R
import com.example.bakery.databinding.DialogAddFeedbackBinding
import com.example.bakery.ui.base.BaseDialogFragment
import javax.inject.Inject

class AddFeedbackDialogFragment @Inject constructor():
    BaseDialogFragment<DialogAddFeedbackBinding, BranchFeedbackViewModel>(),
    FeedBackDialogListener {
    var onAccept: ((String, String) -> Unit)? = null
    var onCancel: (() -> Unit)? = null

    override val viewModel: BranchFeedbackViewModel by viewModels()
    override val layoutId: Int
        get() = R.layout.dialog_add_feedback

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.listener = this@AddFeedbackDialogFragment
    }

    override fun onAccept() {
        onAccept?.invoke(viewBinding.edtName.text.toString(), viewBinding.edtFeedback.text.toString())
    }

    override fun onCancel() {
        viewBinding.edtFeedback.text?.clear()
        viewBinding.edtName.text?.clear()
        onCancel?.invoke()
    }

    override fun onStart() {
        super.onStart()
        val window = dialog?.window
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        window?.setLayout(width, height)
    }
}

interface FeedBackDialogListener {
    fun onAccept()

    fun onCancel()
}