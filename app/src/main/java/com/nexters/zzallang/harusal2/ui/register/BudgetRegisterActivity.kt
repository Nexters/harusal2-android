package com.nexters.zzallang.harusal2.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityRegisterBudgetBinding
import com.nexters.zzallang.harusal2.ui.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class BudgetRegisterActivity : BaseActivity<ActivityRegisterBudgetBinding>() {
    override fun layoutRes(): Int = R.layout.activity_register_budget
    override val viewModel: BudgetRegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override fun bindingView() {
        super.bindingView()

        //TODO - 양방향 무한 호출 개선
        viewModel.budget.observe(this, Observer {
            viewModel.budgetChanged(it)
        })

        binding.btnNext.setOnClickListener {
            viewModel.saveBudget()
            val intent = Intent(this, StartDayDefaultRegisterActivity::class.java)
            this.startActivity(intent)
        }

        binding.btnClose.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
        }
    }
}