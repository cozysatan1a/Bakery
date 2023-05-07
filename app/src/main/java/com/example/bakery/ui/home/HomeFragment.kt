package com.example.bakery.ui.home

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bakery.BR
import com.example.bakery.R
import com.example.bakery.databinding.FragmentHomeBinding
import com.example.bakery.ui.base.BaseFragment
import com.example.bakery.ui.branch.branch_management.BranchManagementFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(), HomeListener {

    override val viewModel: HomeViewModel by viewModels()
    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            listener = this@HomeFragment
            setVariable(BR.viewModel, viewModel)
        }
        viewModel.getCurrentUserFirebase {
            binding.tvUsername.text = it?.name
            binding.btnManageUser.isVisible = (it?.admin == "1")
            binding.btnStorage.isVisible = it?.head == true || (it?.admin == "1")
        }

    }

    override fun navigateToCustomerManagement() {
        findNavController().navigate(R.id.openCustomerManagement)
    }

    override fun navigateToStorageManagement() {
        viewModel.getCurrentUserFirebase {
            if (it?.admin == "1") {
                findNavController().navigate(R.id.openStorageManagement)
            }
            else if (it?.admin == "0" && it.head == true) {
                val action = HomeFragmentDirections.fromHomeGoToBranchInfo().setId(it.branch)
                findNavController().navigate(action)
            }
        }
    }

    override fun navigateToUserManagement() {
        findNavController().navigate(R.id.openUserManagementFragment)
    }

    override fun navigateToMenuManagement() {
        findNavController().navigate(R.id.goToMenu)
    }


}