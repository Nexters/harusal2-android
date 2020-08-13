package com.nexters.zzallang.harusal2.ui.main

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityMainBinding
import com.nexters.zzallang.harusal2.ui.main.adapter.MainStatementAdapter
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

        binding.recyclerStatement.layoutManager = LinearLayoutManager(this)
        binding.recyclerStatement.adapter = MainStatementAdapter(this).apply {
            clearMainStatementList()
            addStatements(arrayListOf(
                MainStatement(1000, "hi"),
                MainStatement(2000, "hi"),
                MainStatement(3000, "hi"),
                MainStatement(4000, "hi"),
                MainStatement(5000, "hi"),
                MainStatement(6000, "hi"),
                MainStatement(7000, "hi"),
                MainStatement(7000, "hi"),
                MainStatement(7000, "hi"),
                MainStatement(7000, "hi"),
                MainStatement(7000, "hi"),
                MainStatement(7000, "hi"),
                MainStatement(7000, "hi"),
                MainStatement(7000, "hi"),
                MainStatement(7000, "hi"),
                MainStatement(7000, "hi"),
                MainStatement(7000, "hi"),
                MainStatement(7000, "hi"),
                MainStatement(7000, "hi"),
                MainStatement(7000, "hi"),
                MainStatement(7000, "hi")
            ))
        }
        binding.recyclerStatement.setHasFixedSize(true)
    }
}