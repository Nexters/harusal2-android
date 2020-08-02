package com.nexters.zzallang.harusal2.ui.main

import android.os.Bundle
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun layoutRes(): Int = R.layout.activity_main
    override val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        when (viewModel.isBudgetEmpty()) {
            true -> supportFragmentManager.beginTransaction().replace(R.id.container, EmptyMainFragment()).commit()
            false -> supportFragmentManager.beginTransaction().replace(R.id.container, DefaultMainFragment()).commit()
        }
    }
}