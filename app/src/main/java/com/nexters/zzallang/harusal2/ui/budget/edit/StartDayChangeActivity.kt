package com.nexters.zzallang.harusal2.ui.budget.edit

import android.content.Intent
import android.os.Bundle
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityChangeDayBinding
import com.nexters.zzallang.harusal2.ui.MainActivity
import com.nexters.zzallang.harusal2.ui.register.StartDayDefaultRegisterActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartDayChangeActivity : BaseActivity<ActivityChangeDayBinding>() {
    override fun layoutRes(): Int = R.layout.activity_change_day
    override val viewModel: StartDayChangeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override fun bindingView() {
        super.bindingView()

        binding.pickerDay.minValue = 1
        binding.pickerDay.maxValue = DateUtils.getLastDayOfMonth()

        binding.pickerDay.setOnValueChangedListener({ picker, oldVal, newVal ->
            viewModel.pickedDayChanged(newVal)
        })

        binding.btnPrev.setOnClickListener { this.startActivity(Intent(this, MainActivity::class.java)) }
        binding.btnReset.setOnClickListener { viewModel.reset() }
        binding.btnComplete.setOnClickListener {
            viewModel.saveBudgetDay()
            this.startActivity(Intent(this, MainActivity::class.java))
        }
    }
}