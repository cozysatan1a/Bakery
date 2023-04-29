package com.example.bakery.ui.edit_profile

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import com.example.bakery.R
import com.example.bakery.databinding.FragmentEditProfileBinding
import com.example.bakery.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding, EditProfileViewModel>(), EditProfileListener {
    override val viewModel: EditProfileViewModel by viewModels()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_edit_profile
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            listener = this@EditProfileFragment
        }
    }

    override fun onBackPressed() {
        activity?.onBackPressed()
    }

    override fun onUpdateProfile() {
        binding.apply {
            val selectedGender: RadioButton =
                binding.root.findViewById(binding.rgGender.checkedRadioButtonId)
            viewModel.updateCurrentUser(
                code = edtCode.text.toString(),
                dob = edtDob.text.toString(),
                gender = selectedGender.text.toString(),
                name = edtName.text.toString()
            )
            activity?.onBackPressed()
        }
    }
}