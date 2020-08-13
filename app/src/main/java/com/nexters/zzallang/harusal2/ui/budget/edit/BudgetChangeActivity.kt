package com.nexters.zzallang.harusal2.ui.budget.edit

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityChangeBudgetBinding
import com.nexters.zzallang.harusal2.ui.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class BudgetChangeActivity : BaseActivity<ActivityChangeBudgetBinding>() {
    override fun layoutRes(): Int = R.layout.activity_change_budget
    override val viewModel: BudgetChangeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override fun bindingView() {
        super.bindingView()

        viewModel.budget.observe(this, Observer {
            viewModel.budgetChanged(it)
            binding.editRegisterBudget.setSelection(binding.editRegisterBudget.text.length)
        })

        binding.btnReset.setOnClickListener {
            viewModel.reset()
        }

        binding.btnPrev.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
        }

        binding.btnComplete.setOnClickListener {
            viewModel.saveBudget()
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
        }
    }
}