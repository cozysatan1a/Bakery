package com.example.bakery.ui.food.food_edit

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.bakery.R
import com.example.bakery.databinding.FragmentFoodEditBinding
import com.example.bakery.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodEditFragment : BaseFragment<FragmentFoodEditBinding, FoodEditViewModel>(), FoodEditListener {
    override val viewModel: FoodEditViewModel by viewModels()
    private val args : FoodEditFragmentArgs by navArgs()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_food_edit
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            listener = this@FoodEditFragment
        }
        viewModel.loadData(args.id)
        viewModel.food.observe(viewLifecycleOwner) {
            binding.layoutName.hint = it?.name
            binding.layoutPrice.hint = it?.price
        }
    }

    override fun onSaveEdit() {
        viewModel.updateFood(uid = args.id, name = binding.edtName.text.toString(), price = binding.edtPrice.text.toString())
        onBackPressed()
    }

    override fun onDelete() {
        viewModel.onDeleteFood(args.id) {
            onBackPressed()
            Toast.makeText(activity, "Xóa món ăn thành công", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }
}