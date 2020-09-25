package com.nexters.zzallang.harusal2.ui.setting

import android.content.Intent
import android.os.Bundle
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivitySettingBinding
import com.nexters.zzallang.harusal2.ui.budget.edit.BudgetEditActivity
import com.nexters.zzallang.harusal2.ui.budget.edit.StartDayEditDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingActivity: BaseActivity<ActivitySettingBinding>() {
    override val viewModel: SettingViewModel by viewModel()

    override fun layoutRes(): Int = R.layout.activity_setting

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun bindingView() {
        super.bindingView()
        binding.ivClose.setOnClickListener {
            finish()
        }

        binding.btnAlarmSetting.setOnClickListener {
            startActivity(Intent(this, AlarmSettingActivity::class.java))
        }

        binding.btnEditBudget.setOnClickListener {
            startActivity(Intent(this, BudgetEditActivity::class.java))
        }

        binding.btnEditStartDay.setOnClickListener {
            val dialog = StartDayEditDialog(this)
            dialog.setOnOKClickedListener {
                viewModel.save()
            }

            dialog.start(viewModel.initContent())
        }
    }
}