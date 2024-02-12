package com.nexters.zzallang.harusal2.ui.budget.edit

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.databinding.ActivityChangeBudgetBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class BudgetEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeBudgetBinding
    private val viewModel: BudgetEditViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeBudgetBinding.inflate(layoutInflater)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        viewModel.budget.observe(this) {
            viewModel.budgetChanged(it)
            when (it) {
                "" -> {
                    binding.tvUnit.setTextColor(this.getColor(R.color.disable_txt))
                    binding.btnComplete.setBackgroundColor(this.getColor(R.color.line))
                    binding.btnComplete.isEnabled = false
                }

                else -> {
                    binding.tvUnit.setTextColor(this.getColor(R.color.default_txt))
                    binding.btnComplete.setBackgroundColor(this.getColor(R.color.default_txt))
                    binding.btnComplete.isEnabled = true
                }
            }

            binding.editRegisterBudget.setSelection(binding.editRegisterBudget.text.length)
        }

        binding.btnPrev.setOnClickListener {
            this.finish()
        }

        binding.btnComplete.setOnClickListener {
            lifecycleScope.launch {
                val isSave = viewModel.saveBudget()

                if (isSave) {
                    this@BudgetEditActivity.finish()
                    Toast.makeText(this@BudgetEditActivity, "생활비를 수정하였습니다.", Toast.LENGTH_LONG).show()

                    return@launch;
                }

                Toast.makeText(this@BudgetEditActivity, "생활비 수정을 실패했습니다.", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.init()
    }
}
