package com.example.bakery.ui.user.user_management

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bakery.R
import com.example.bakery.databinding.FragmentUserManagementBinding
import com.example.bakery.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserManagementFragment :
    BaseFragment<FragmentUserManagementBinding, UserManagementViewModel>(), UserManagementListener {
    override val viewModel: UserManagementViewModel by viewModels()
    override fun getLayoutRes(): Int {
        return R.layout.fragment_user_management
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            listener = this@UserManagementFragment
            rvUserList.layoutManager = LinearLayoutManager(activity)
        }
        viewModel.getAllUser {
            binding.rvUserList.adapter =
                UserRecyclerAdapter<Any?>(it, UserRecyclerAdapter.OnClickListener { user ->
                    val action =
                        UserManagementFragmentDirections.openUserProfile().setUid(user?.uid)
                    findNavController().navigate(action)
                })
            binding.apply {
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                    androidx.appcompat.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if (query?.isEmpty() == true) {
                            binding.rvUserList.adapter = UserRecyclerAdapter<Any?>(
                                it,
                                UserRecyclerAdapter.OnClickListener { user ->
                                    val action = UserManagementFragmentDirections.openUserProfile()
                                        .setUid(user?.uid)
                                    findNavController().navigate(action)
                                })
                        } else {
                            val list = it.filter {
                                query?.let { query -> it?.name?.contains(query) } == true
                            }
                            binding.rvUserList.adapter = UserRecyclerAdapter<Any?>(
                                list,
                                UserRecyclerAdapter.OnClickListener { user ->
                                    val action = UserManagementFragmentDirections.openUserProfile()
                                        .setUid(user?.uid)
                                    findNavController().navigate(action)
                                })
                        }
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        binding.rvUserList.adapter = UserRecyclerAdapter<Any?>(
                            it,
                            UserRecyclerAdapter.OnClickListener { user ->
                                val action = UserManagementFragmentDirections.openUserProfile()
                                    .setUid(user?.uid)
                                findNavController().navigate(action)
                            })
                        return false
                    }
                })
            }
        }
    }

    override fun createAccount() {
        findNavController().navigate(R.id.openSignUp)
    }

    override fun onBackPressed() {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }
}