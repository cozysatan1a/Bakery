package com.example.bakery.ui.food.create_food

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import com.example.bakery.R
import com.example.bakery.data.model.Customer
import com.example.bakery.data.model.FoodInfo
import com.example.bakery.data.model.loadFoodList
import com.example.bakery.databinding.FragmentCreateFoodBinding
import com.example.bakery.ui.base.BaseFragment
import com.example.bakery.ui.food.food_management.FoodListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateFoodFragment : BaseFragment<FragmentCreateFoodBinding, CreateFoodViewModel>(), FoodListener {
    override val viewModel: CreateFoodViewModel by viewModels()
    private val fStore = FirebaseFirestore.getInstance()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_create_food
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listener = this@CreateFoodFragment
    }

    override fun onCreateNewFood() {
        TODO("Not yet implemented")
    }

    override fun onBackPressed() {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }

    override fun onCreateFood() {
        val documentId : String = fStore.collection("Foods").document().id
        val documentReference: DocumentReference = fStore.collection("Foods").document(documentId)

        val food = FoodInfo(
            id = documentId,
            name = binding.edtName.text.toString(),
            price = binding.edtPrice.text.toString()
        )
        documentReference.set(food).addOnSuccessListener {
            Snackbar.make(
                binding.btnAdd,
                "Tạo món vào menu thành công",
                Snackbar.LENGTH_SHORT
            ).show()
            loadFoodList()
            onBackPressed()
        }
    }
}