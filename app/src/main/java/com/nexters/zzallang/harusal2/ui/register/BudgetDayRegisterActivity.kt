package com.nexters.zzallang.harusal2.ui.register

import android.os.Bundle
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivtyRegisterBudgetDayBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class BudgetDayRegisterActivity : BaseActivity<ActivtyRegisterBudgetDayBinding>(){
    override fun layoutRes(): Int = R.layout.activty_register_budget_day
    override val viewModel: BudgetDayRegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override fun bindingView() {
        super.bindingView()
        binding.tvDescription.text = viewModel.getDescription()
        binding.btnComplete.text = viewModel.getButtonText()
        binding.btnPrev.setOnClickListener { viewModel.toPrev(this) }
        binding.btnNext.setOnClickListener {
            viewModel.toNext(this) }
        binding.btnComplete.setOnClickListener { viewModel.toComplete(this) }
        binding.btnClose.setOnClickListener { viewModel.toClose(this) }
    }
}