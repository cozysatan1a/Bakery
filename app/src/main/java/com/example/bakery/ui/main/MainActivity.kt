package com.example.bakery.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.bakery.R
import com.example.bakery.databinding.ActivityMainBinding
import com.example.bakery.ui.base.BaseActivity

class MainActivity() : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val viewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController
    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.activity_main_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigationView = binding.mainBottomNav
        setupWithNavController(bottomNavigationView, navController)
    }
}