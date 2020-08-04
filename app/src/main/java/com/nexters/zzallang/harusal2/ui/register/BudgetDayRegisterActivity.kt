package com.nexters.zzallang.harusal2.ui.register

import android.content.Intent
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

        binding.btnPrev.setOnClickListener {
            val intent = Intent(this, BudgetRegisterActivity::class.java)
            this.startActivity(intent)
        }

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, BudgetDayRegisterActivity::class.java)
            this.startActivity(intent)
        }

        binding.btnComplete.setOnClickListener {
            viewModel.saveBudgetDay()
            val intent = Intent(this, BudgetRegisterActivity::class.java)
            this.startActivity(intent)
        }

        binding.btnClose.setOnClickListener {
            val intent = Intent(this, BudgetRegisterActivity::class.java)
            this.startActivity(intent)
        }
    }
}