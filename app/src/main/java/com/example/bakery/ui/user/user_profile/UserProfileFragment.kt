package com.example.bakery.ui.user.user_profile

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bakery.R
import com.example.bakery.databinding.FragmentUserProfileBinding
import com.example.bakery.ui.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileFragment : BaseFragment<FragmentUserProfileBinding, UserProfileViewModel>(),
    UserProfileListener {
    override val viewModel: UserProfileViewModel by viewModels()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_user_profile
    }

    private val args: UserProfileFragmentArgs by navArgs()

    private val fAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listener = this@UserProfileFragment
        viewModel.apply {
            val currentUserId = fAuth.currentUser?.uid
            binding.btnDelete.isVisible = currentUserId != args.uid

            getCurrentUserFirebase(uid = args.uid) { user ->
                binding.apply {
                    tvDob.text = user?.dob
                    tvEmail.text = user?.email
                    tvGender.text = user?.gender
                    tvName.text = user?.name
                    viewModel.getCurrentBranchFirebase(user?.branch) {
                        tvBranch.text = it?.address
                    }
                    tvRole.text =
                        if (user?.admin == "1") getString(R.string.role_admin) else getString(
                            R.string.role_employee
                        )
                    ivAvatar.setImageResource(if (user?.gender == "Nam") R.drawable.img_avatar_male else R.drawable.img_avatar_female)
                    btnDelete.setOnClickListener {
                        viewModel.deleteUser(user?.uid) {
                            activity?.onBackPressed()
                            Snackbar.make(
                                btnDelete,
                                "Xóa tài khoản thành công",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    override fun onClickEditInfo() {
        val action =
            UserProfileFragmentDirections.openEditUser()
                .setUid(args.uid)
        findNavController().navigate(action)
    }

    override fun onClickDeleteAccount() {
        viewModel.deleteUser(args.uid) {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        activity?.onBackPressed()
    }

}