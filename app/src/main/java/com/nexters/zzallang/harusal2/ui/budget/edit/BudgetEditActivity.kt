package com.nexters.zzallang.harusal2.ui.budget.edit

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityChangeBudgetBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class BudgetEditActivity : BaseActivity<ActivityChangeBudgetBinding>() {
    override fun layoutRes(): Int = R.layout.activity_change_budget
    override val viewModel: BudgetEditViewModel by viewModel()
    private val job = Job()
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

        binding.btnPrev.setOnClickListener {
            this.finish()
        }

        binding.btnComplete.setOnClickListener {
            CoroutineScope(Dispatchers.Main + job).launch {
                val isSave = viewModel.saveBudget()
                if (isSave) {
                    this@BudgetEditActivity.finish()
                }
                Toast.makeText(this@BudgetEditActivity, "저장에 실패했습니다.", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.init()
    }
}