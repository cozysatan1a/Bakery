package com.example.bakery.ui.signup

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import com.example.bakery.R
import com.example.bakery.data.model.Branch
import com.example.bakery.databinding.FragmentSignupBinding
import com.example.bakery.ui.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignupBinding, SignUpViewModel>(), SignUpListener {
    override val viewModel: SignUpViewModel by viewModels()
    private val fStore = FirebaseFirestore.getInstance()
    override fun getLayoutRes(): Int {
        return R.layout.fragment_signup
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listener = this@SignUpFragment
        viewModel.getAllBranches {
            it.add(0, Branch("Chi nhánh", id = "0"))
            val adapter: ArrayAdapter<*> = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                it
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerFood.adapter = adapter
        }
    }

    override fun onClickSignUp() {
        validateFields {
            val firebaseDefaultApp = Firebase.auth.app
            val signUpAppName = firebaseDefaultApp.name + "_signUp"

            val signUpApp = try {
                FirebaseApp.initializeApp(
                    requireContext(),
                    firebaseDefaultApp.options,
                    signUpAppName
                )
            } catch (e: IllegalStateException) {
                FirebaseApp.getInstance(signUpAppName)
            }

            val signUpFirebaseAuth = Firebase.auth(signUpApp)
            signUpFirebaseAuth.createUserWithEmailAndPassword(
                binding.edtEmail.text.toString(),
                binding.edtPassword.text.toString()
            )
                .addOnSuccessListener {
                    val user: FirebaseUser? = signUpFirebaseAuth.currentUser
                    val documentReference: DocumentReference? =
                        user?.uid?.let { uid -> fStore.collection("Users").document(uid) }
                    val userInfo: MutableMap<String, Any> = hashMapOf()
                    val selectedGender: RadioButton =
                        binding.root.findViewById(binding.rgGender.checkedRadioButtonId)
                    val branchId = (binding.spinnerFood.selectedItem as Branch).id?.let {
                        it
                    }
                    val isBranchMaster = binding.rbBranchMaster.isChecked
                    userInfo["name"] = binding.edtName.text.toString()
                    userInfo["gender"] = selectedGender.text.toString()
                    userInfo["email"] = binding.edtEmail.text.toString()
                    userInfo["dob"] = binding.edtDob.text.toString()
                    userInfo["code"] = binding.edtCode.text.toString()
                    userInfo["admin"] =
                        if (binding.rgRole.checkedRadioButtonId == binding.rbAdmin.id) "1" else "0"
                    userInfo["uid"] = user?.uid.toString()
                    userInfo["branch"] = branchId!!
                    userInfo["head"] = isBranchMaster
                    documentReference?.set(userInfo)
                    Snackbar.make(
                        binding.btnLogin,
                        "Tạo tài khoản thành công",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    signUpFirebaseAuth.signOut()
                    onBackPressed()
                }
                .addOnFailureListener {
                    signUpApp.delete()
                    when (it) {
                        is FirebaseAuthWeakPasswordException -> "Mật khẩu không đủ mạnh"
                        else -> {
                            Snackbar.make(
                                binding.btnLogin,
                                "Tạo tài khoản thất bại. Lỗi: $it",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }

                }

        }
    }

    override fun onBackPressed() {
        activity?.onBackPressed()
    }

    private fun validateFields(isValid: () -> Unit) {
        if (binding.let {
                it.edtEmail.text.isNullOrBlank()
                        || it.edtCode.text.isNullOrBlank()
                        || it.edtDob.text.isNullOrBlank()
                        || it.edtEmail.text.isNullOrBlank()
                        || it.edtPassword.text.isNullOrBlank()
                        || it.edtRePassword.text.isNullOrBlank()
            }) {
            Snackbar.make(binding.btnLogin, "Các trường không được để trống", Snackbar.LENGTH_SHORT)
                .show()
        } else if (binding.edtPassword.text.toString() != binding.edtRePassword.text.toString()) {
            binding.edtPassword.error = "Mật khẩu không trùng khớp"
            binding.edtRePassword.error = "Mật khẩu không trùng khớp"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.edtEmail.text.toString()).matches()) {
            //invalid email format
            binding.edtEmail.error = "Email không đúng định dạng"
        } else {
            isValid.invoke()
        }
    }
}