package com.example.bakery.ui.branch.create_branch

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.bakery.R
import com.example.bakery.databinding.FragmentCreateBranchBinding
import com.example.bakery.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateBranchFragment : BaseFragment<FragmentCreateBranchBinding, CreateBranchViewModel>(),
    CreateBranchListener {
    override val viewModel: CreateBranchViewModel by viewModels()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_create_branch
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listener = this@CreateBranchFragment
    }

    override fun onBackPressed() {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }

    override fun onCreateBranch() {
        if (
            binding.edtAddress.text.toString().isNotBlank() && binding.edtPhone.text.toString()
                .isNotBlank()
        ) {
            viewModel.createBranch(
                address = binding.edtAddress.text.toString(),
                phone = binding.edtPhone.text.toString()
            ) {
                onBackPressed()
                Toast.makeText(activity, "Tạo chi nhánh thành công", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(activity, "Các trường không được để trống", Toast.LENGTH_SHORT).show()
        }

    }
}