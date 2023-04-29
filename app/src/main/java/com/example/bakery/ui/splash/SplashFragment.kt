package com.example.bakery.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bakery.R
import com.example.bakery.data.model.FoodInfo
import com.example.bakery.data.model.foodList
import com.example.bakery.data.model.loadFoodList
import com.example.bakery.databinding.FragmentSplashBinding
import com.example.bakery.ui.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>() {
    override val viewModel: SplashViewModel by viewModels()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_splash
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadFoodList()
        Handler(Looper.getMainLooper()).postDelayed(
            {
                if (FirebaseAuth.getInstance().currentUser == null) {
                    findNavController().navigate(R.id.openLogin)
                } else {
                    findNavController().navigate(R.id.goToMain)
                }
            },
            2000
        )
    }

}