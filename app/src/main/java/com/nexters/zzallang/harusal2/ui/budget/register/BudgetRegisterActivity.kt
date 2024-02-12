package com.nexters.zzallang.harusal2.ui.budget.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.util.IntentUtils
import com.nexters.zzallang.harusal2.databinding.ActivityRegisterBudgetBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class BudgetRegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBudgetBinding
    private val viewModel: BudgetRegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBudgetBinding.inflate(layoutInflater)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        viewModel.budget.observe(this) {
            when (it) {
                "" -> {
                    binding.layoutWarningWrapper.visibility = View.INVISIBLE
                    binding.tvUnit.setTextColor(this.getColor(R.color.disable_txt))
                    binding.btnNext.setBackgroundColor(this.getColor(R.color.line))
                    binding.btnNext.isEnabled = false
                }

                else -> {
                    binding.layoutWarningWrapper.visibility = View.VISIBLE
                    binding.tvUnit.setTextColor(this.getColor(R.color.default_txt))
                    binding.btnNext.setBackgroundColor(this.getColor(R.color.btn_black))
                    binding.btnNext.isEnabled = true
                }
            }
            viewModel.budgetChanged(it)
        }

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, StartDayDefaultRegisterActivity::class.java)
            intent.putExtra("budget", viewModel.budget.value)
            this.startActivity(intent)
            this.finish()
        }

        binding.btnClose.setOnClickListener {
            this.startActivity(IntentUtils.getEmptyMainActivityIntent(this))
            this.finish()
        }
    }
}
