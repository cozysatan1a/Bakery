package com.example.bakery.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bakery.R
import com.example.bakery.databinding.FragmentProfileBinding
import com.example.bakery.ui.base.BaseFragment
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

}