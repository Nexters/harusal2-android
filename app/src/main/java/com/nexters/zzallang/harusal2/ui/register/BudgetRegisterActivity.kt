package com.nexters.zzallang.harusal2.ui.register

import android.os.Bundle
import androidx.lifecycle.Observer
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivtyRegisterBudgetBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class BudgetRegisterActivity : BaseActivity<ActivtyRegisterBudgetBinding>() {
    override fun layoutRes(): Int = R.layout.activty_register_budget
    override val viewModel: BudgetRegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override fun bindingView() {
        super.bindingView()

        viewModel.budget.observe(this, Observer {
            viewModel.budgetChanged(it)
        })

        binding.btnNext.setOnClickListener { viewModel.toNext() }
    }
}