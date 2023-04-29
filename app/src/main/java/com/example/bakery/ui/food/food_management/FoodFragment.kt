package com.example.bakery.ui.food.food_management

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bakery.R
import com.example.bakery.databinding.FragmentFoodBinding
import com.example.bakery.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodFragment : BaseFragment<FragmentFoodBinding, FoodViewModel>(), FoodListener {
    override val viewModel: FoodViewModel by viewModels()

    override fun getLayoutRes(): Int {
        return  R.layout.fragment_food
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadData()
        binding.apply {
            listener = this@FoodFragment
            rvFood.layoutManager = LinearLayoutManager(activity)
            viewModel.foods.observe(viewLifecycleOwner) {
                rvFood.adapter = it?.let {
                    FoodAdapter(it, FoodAdapter.OnClickListener{ food ->
                        val action = FoodFragmentDirections.goToEditFood().setId(food?.id)
                        findNavController().navigate(action)
                    })
                }
            }

        }
    }

    override fun onCreateNewFood() {
        findNavController().navigate(R.id.goToCreateFood)
    }

    override fun onBackPressed() {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }

    override fun onCreateFood() {
        TODO("Not yet implemented")
    }
}