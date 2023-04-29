package com.example.bakery.ui.start

import androidx.activity.viewModels
import com.example.bakery.R
import com.example.bakery.databinding.ActivityStartBinding
import com.example.bakery.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartActivity : BaseActivity<ActivityStartBinding, StartViewModel>() {
    override fun getLayoutRes(): Int {
        return R.layout.activity_start
    }

    override val viewModel: StartViewModel by viewModels()
}