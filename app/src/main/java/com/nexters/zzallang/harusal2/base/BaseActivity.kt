package com.nexters.zzallang.harusal2.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class BaseActivity<B: ViewDataBinding>: AppCompatActivity() {
    abstract val viewModel: ViewModel
    protected lateinit var binding: B

    @LayoutRes abstract fun layoutRes() : Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutRes())

        bindingView()
    }

    open fun bindingView() {

    }
}