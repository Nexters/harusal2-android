package com.nexters.zzallang.harusal2.ui.budget.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityRegisterBudgetBinding
import com.nexters.zzallang.harusal2.ui.main.MainActivity
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

        viewModel.budget.observe(this, Observer {

            when (it) {
                "" -> {
                    binding.layoutWarningWrapper.visibility = View.INVISIBLE
                    binding.tvUnit.setTextColor(this.getColor(R.color.colorGray))
                }
                else -> {
                    binding.layoutWarningWrapper.visibility = View.VISIBLE
                    binding.tvUnit.setTextColor(this.getColor(R.color.colorDarkBlack))
                }
            }
            viewModel.budgetChanged(it)
        })

        binding.btnNext.setOnClickListener {
//            viewModel.saveBudget()
            val intent = Intent(this, StartDayDefaultRegisterActivity::class.java)
            intent.putExtra("budget", viewModel.budget.value)
            this.startActivity(intent)
            this.finish()
        }

        binding.btnClose.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
            this.finish()
        }
    }
}