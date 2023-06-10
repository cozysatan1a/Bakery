package com.example.bakery.ui.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bakery.R
import com.example.bakery.databinding.FragmentLoginBinding
import com.example.bakery.ui.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
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
                when (it) {
                    is FirebaseNetworkException -> Snackbar.make(binding.btnLogin, "Vui lòng kiểm tra kết nối mạng", Snackbar.LENGTH_SHORT)
                        .show()
                    is FirebaseAuthInvalidCredentialsException -> Snackbar.make(binding.btnLogin, "Email/Mật khẩu sai", Snackbar.LENGTH_SHORT)
                        .show()
                    is FirebaseAuthInvalidUserException -> Snackbar.make(binding.btnLogin, "Tài khoản không hợp lệ", Snackbar.LENGTH_SHORT)
                        .show()
                    else -> Snackbar.make(binding.btnLogin, "Đăng nhập lỗi. \n Mã lỗi: $it", Snackbar.LENGTH_SHORT)
                        .show()
                }

            }
        }

    }

    override fun onClickForgotPassword() {
        if (binding.edtEmail.text.isNullOrBlank()) {
            Toast.makeText(requireContext(), "Nhập email sau đó bấm Quên mật khẩu", Toast.LENGTH_SHORT).show()
        } else {
            fAuth.sendPasswordResetEmail(binding.edtEmail.text.toString()).addOnSuccessListener {
                Toast.makeText(requireContext(), "Đã gửi email reset mật khẩu đến ${binding.edtEmail.text.toString()}", Toast.LENGTH_SHORT).show()
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