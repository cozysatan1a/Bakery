package com.example.bakery.ui.user.edit_user

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.bakery.R
import com.example.bakery.data.model.Branch
import com.example.bakery.databinding.FragmentEditUserBinding
import com.example.bakery.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditUserFragment : BaseFragment<FragmentEditUserBinding, EditUserViewModel>(),
    EditUserListener {
    override val viewModel: EditUserViewModel by viewModels()

    private val args : EditUserFragmentArgs by navArgs()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_edit_user
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listener = this@EditUserFragment
        viewModel.apply {
            getAllBranches {
                it.add(0, Branch(address = "Chi nhánh", id= "0"))
                val adapter: ArrayAdapter<*> = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    it
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerBranch.adapter = adapter
            }
        }
    }

    override fun onBackPressed() {
        activity?.onBackPressed()
    }

    override fun onUpdateUser() {
        binding.apply {
            val selectedGender: RadioButton =
                binding.root.findViewById(binding.rgGender.checkedRadioButtonId)
            val selectedBranch = (binding.spinnerBranch.selectedItem as Branch).id!!
            viewModel.updateCurrentUser(
                args.uid,
                code = edtCode.text.toString(),
                dob = edtDob.text.toString(),
                gender = selectedGender.text.toString(),
                name = edtName.text.toString(),
                branch = selectedBranch
            )
            onBackPressed()
        }

    }
}