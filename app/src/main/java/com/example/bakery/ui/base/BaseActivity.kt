package com.example.bakery.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<ViewBinding : ViewDataBinding, ViewModel : BaseViewModel> : AppCompatActivity() {
    @LayoutRes
    abstract fun getLayoutRes(): Int

    protected abstract val viewModel : ViewModel

    val binding by lazy {
        DataBindingUtil.setContentView(this, getLayoutRes()) as ViewBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}