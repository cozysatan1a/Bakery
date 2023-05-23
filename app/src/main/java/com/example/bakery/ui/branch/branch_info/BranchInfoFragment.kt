package com.example.bakery.ui.branch.branch_info

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bakery.R
import com.example.bakery.databinding.FragmentBranchInfoBinding
import com.example.bakery.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BranchInfoFragment : BaseFragment<FragmentBranchInfoBinding, BranchInfoViewModel>(),
    BranchInfoListener {
    override val viewModel: BranchInfoViewModel by viewModels()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_branch_info
    }

    private val args: BranchInfoFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            listener = this@BranchInfoFragment
        }
        viewModel.apply {
            getCurrentBranchFirebase(args.id) {
                binding.apply {
                    tvAddress.text = it?.address
                    tvPhone.text = it?.phone
                }
            }
            getAllUsersInCurrentBranch(args.id, {
                binding.apply {
                    llHeader.isVisible = (it.size > 0)
                    rvUserList.layoutManager = LinearLayoutManager(activity)
                    rvUserList.adapter =
                        EmployeeRecyclerAdapter(it, EmployeeRecyclerAdapter.OnClickListener {
                            val action = BranchInfoFragmentDirections.goToUserProfile().setUid(it?.uid)
                            findNavController().navigate(action)
                        })
                    llHeader.isVisible = it.size > 0
                }
            }, {
                Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show()
            })
        }
    }

    override fun onBackPressed() {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }

    override fun onEditBranch() {
        val action = BranchInfoFragmentDirections.goToEditBranch().setId(args.id)
        findNavController().navigate(action)
    }

    override fun onDeleteBranch() {
        viewModel.deleteBranch(args.id) {
            onBackPressed()
        }
    }

    override fun onGoToBranchData() {
        val action = BranchInfoFragmentDirections.goToBranchData().setId(args.id)
        findNavController().navigate(action)
    }

    override fun onGoToFeedback() {
        val action = BranchInfoFragmentDirections.goToFeedBack().setUid(args.id)
        findNavController().navigate(action)
    }
}