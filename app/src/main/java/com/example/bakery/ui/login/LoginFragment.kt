package com.example.bakery.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bakery.R
import com.example.bakery.databinding.FragmentLoginBinding
import com.example.bakery.ui.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(), LoginListener {
    override val viewModel: LoginViewModel by viewModels()
    private val fAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun getLayoutRes(): Int {
        return R.layout.fragment_login
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            listener = this@LoginFragment
        }
    }

    override fun onClickLogin() {
        validateFields {
            fAuth.signInWithEmailAndPassword(
                binding.edtEmail.text.toString(),
                binding.edtPassword.text.toString()
            ).addOnSuccessListener {
                Snackbar.make(binding.btnLogin, "Đăng nhập thành công", Snackbar.LENGTH_SHORT)
                    .show()
                findNavController().navigate(R.id.loginToMain)
            }.addOnFailureListener {
                Snackbar.make(binding.btnLogin, "Đăng nhập thất bại.\nMã lỗi: $it", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

    }

    private fun validateFields(callback: () -> Unit) {
        if (binding.edtEmail.text.isNullOrBlank() || binding.edtPassword.text.isNullOrBlank()) {
            Snackbar.make(
                binding.btnLogin,
                "Email hoặc mật khẩu không được để trống",
                Snackbar.LENGTH_SHORT
            ).show()
        } else {
            callback.invoke()
        }
    }
}