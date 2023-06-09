package com.example.bakery.ui.profile

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.bakery.R
import com.example.bakery.databinding.DialogChangePasswordBinding
import com.example.bakery.ui.base.BaseDialogFragment
import javax.inject.Inject

class ChangePasswordDialogFragment @Inject constructor() :
    BaseDialogFragment<DialogChangePasswordBinding, ProfileViewModel>(),
    ChangePasswordDialogListener {
    var onAccept: ((String, String, String, String) -> Unit)? = null
    var onCancel: (() -> Unit)? = null

    override val viewModel: ProfileViewModel by viewModels()
    override val layoutId: Int
        get() = R.layout.dialog_change_password

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.listener = this@ChangePasswordDialogFragment
    }

    override fun onAccept() {
        onAccept?.invoke(
            viewBinding.edtEmail.text.toString(),
            viewBinding.edtCurrentPassword.text.toString(),
            viewBinding.edtName.text.toString(),
            viewBinding.edtFeedback.text.toString()
        )
        viewBinding.edtFeedback.text?.clear()
        viewBinding.edtName.text?.clear()
        viewBinding.edtEmail.text?.clear()
        viewBinding.edtCurrentPassword.text?.clear()
    }

    override fun onCancel() {
        viewBinding.edtFeedback.text?.clear()
        viewBinding.edtName.text?.clear()
        viewBinding.edtEmail.text?.clear()
        viewBinding.edtCurrentPassword.text?.clear()
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

interface ChangePasswordDialogListener {
    fun onAccept()

    fun onCancel()
}
