package com.nexters.zzallang.harusal2.ui.main

import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieDrawable
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityMainBinding
import com.nexters.zzallang.harusal2.ui.main.adapter.MainStatementAdapter
import com.nexters.zzallang.harusal2.ui.main.decoration.MainStatementDecoration
import com.nexters.zzallang.harusal2.ui.main.model.MainStatement
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun layoutRes(): Int = R.layout.activity_main
    override val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override fun bindingView() {
        super.bindingView()

        setBackgroundColor(R.color.test)

        binding.ivEmoji.playAnimation()
        binding.ivEmoji.repeatCount = LottieDrawable.INFINITE

        binding.rcvStatement.layoutManager = LinearLayoutManager(this)
        binding.rcvStatement.addItemDecoration(MainStatementDecoration())
        binding.rcvStatement.adapter = MainStatementAdapter(this).apply {
            clearMainStatementList()
            addStatements(arrayListOf(
            ))
        }
        binding.rcvStatement.setHasFixedSize(true)
    }

    private fun setBackgroundColor(@ColorRes resId: Int) {
        val color = resources.getColor(resId, null)
        binding.layoutWhole.setBackgroundColor(color)
        binding.layoutAppbar.setBackgroundColor(color)

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }
}