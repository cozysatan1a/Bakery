package com.example.bakery.ui.branch.edit_branch

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.bakery.R
import com.example.bakery.databinding.FragmentEditBranchBinding
import com.example.bakery.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditBranchFragment : BaseFragment<FragmentEditBranchBinding, EditBranchViewModel>(),
    EditBranchListener {
    override val viewModel: EditBranchViewModel by viewModels()
    private val args: EditBranchFragmentArgs by navArgs()
    override fun getLayoutRes(): Int {
        return R.layout.fragment_edit_branch
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listener = this@EditBranchFragment
    }

    override fun onBackPressed() {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }

    override fun onUpdateInfo() {
        viewModel.updateBranch(
            args.id,
            binding.edtAddress.text.toString(),
            binding.edtPhone.text.toString()
        )
        onBackPressed()
    }
}