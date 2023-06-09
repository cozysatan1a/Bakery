package com.example.bakery.ui.profile

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bakery.R
import com.example.bakery.databinding.FragmentProfileBinding
import com.example.bakery.ui.base.BaseFragment
import com.example.bakery.ui.branch.branch_feedback.BranchFeedbackAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>(), ProfileListener {
    override val viewModel: ProfileViewModel by viewModels()
    override fun getLayoutRes(): Int {
        return R.layout.fragment_profile
    }

    private val fAuth : FirebaseAuth = FirebaseAuth.getInstance()

    private val args : ProfileFragmentArgs by navArgs()

    private var dialogChangePassword = ChangePasswordDialogFragment()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listener = this@ProfileFragment
        viewModel.apply {
            getCurrentUserFirebase(uid = if (args.uid == null) fAuth.currentUser?.uid else args.uid) {
                binding.apply {
                    tvDob.text = it?.dob
                    tvEmail.text = it?.email
                    tvGender.text = it?.gender
                    tvName.text = it?.name
                    tvRole.text =
                        if (it?.admin == "1") getString(R.string.role_admin) else getString(
                            R.string.role_employee
                        )
                    ivAvatar.setImageResource(if (it?.gender == "Nam") R.drawable.img_avatar_male else R.drawable.img_avatar_female)
                }
            }
        }
    }

    override fun onBackPressed() {
        activity?.onBackPressed()
    }

    override fun onClickEditInfo() {
        findNavController().navigate(R.id.goToEditProfile)
    }

    override fun onClickLogOut() {
        fAuth.signOut()
        findNavController().navigate(R.id.backToSplash)
    }

    override fun onChangePassword() {
        dialogChangePassword.apply dialog@{
            this@ProfileFragment.childFragmentManager.apply manager@{
                this@dialog.onAccept = { email, currentPassword, password, newPassword ->
                    if (password.isNullOrBlank() || newPassword.isNullOrBlank() || email.isNullOrBlank() || currentPassword.isNullOrBlank()) {
                        Toast.makeText(
                            requireContext(),
                            "Các trường không được để trống",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (password != newPassword) {
                        Toast.makeText(
                            requireContext(),
                            "Mật khẩu không trùng khớp",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        viewModel.updatePassword(email, currentPassword, newPassword,
                        onSuccess =  {
                            Toast.makeText(requireContext(), "Mật khẩu được thay đổi thành công", Toast.LENGTH_SHORT).show()
                            this@dialog.dismiss()
                        },
                        onFailure = {
                            Snackbar.make(binding.root, "Mật khẩu gặp lỗi khi thay đổi \n Lỗi: ${it.toString().substringAfter("Exception:")}", Snackbar.LENGTH_LONG).show()
                            Log.d(TAG, "onChangePassword: ==== $it")
                            this@dialog.dismiss()
                        })
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