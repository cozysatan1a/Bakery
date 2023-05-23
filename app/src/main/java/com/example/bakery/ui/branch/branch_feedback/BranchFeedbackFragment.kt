package com.example.bakery.ui.branch.branch_feedback

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bakery.R
import com.example.bakery.data.model.Feedback
import com.example.bakery.databinding.FragmentBranchFeedbackBinding
import com.example.bakery.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BranchFeedbackFragment :
    BaseFragment<FragmentBranchFeedbackBinding, BranchFeedbackViewModel>(), BranchFeedbackListener {
    override val viewModel: BranchFeedbackViewModel by viewModels()
    private val args : BranchFeedbackFragmentArgs by navArgs()
    private var dialogFeedback = AddFeedbackDialogFragment()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_branch_feedback
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listener = this@BranchFeedbackFragment
        binding.apply {
            viewModel.getAllFeedback(args.uid) {
                rvUserList.layoutManager = LinearLayoutManager(activity)
                rvUserList.adapter = BranchFeedbackAdapter(it.toMutableList())
            }
        }
    }

    override fun onBackPressed() {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }

    override fun onAddFeedback() {
        dialogFeedback.apply dialog@{
            this@BranchFeedbackFragment.childFragmentManager.apply manager@{
                this@dialog.onAccept = { name, feedback ->
                    if (feedback.isNullOrBlank()) {
                        Toast.makeText(requireContext(), "Feedback không được để trống", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.updateBranch(args.uid, name, feedback) {
                            this@dialog.dismiss()
                        }
                    }
                }
                this@dialog.onCancel = {
                    this@dialog.dismiss()
                }
                show(
                    this@manager,
                    isShow = true
                )
            }
        }
    }

}