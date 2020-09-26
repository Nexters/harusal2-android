package com.nexters.zzallang.harusal2.ui.setting

import android.content.Intent
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivitySettingBinding
import com.nexters.zzallang.harusal2.ui.budget.edit.BudgetEditActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class SettingActivity: BaseActivity<ActivitySettingBinding>() {
    override val viewModel: SettingViewModel by viewModel()
    override fun layoutRes(): Int = R.layout.activity_setting

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
            val dialog = SettingDialog(this)
            dialog.setOnOKClickedListener {
                viewModel.editBudgetDate()
            }

            val todayDate: Int = Date().date
            dialog.start(
                getString(R.string.dialog_edit_budget_date_title, todayDate, todayDate),
                getString(R.string.dialog_edit_budget_date_complete_button, todayDate)
            )
        }

        binding.btnResetData.setOnClickListener {
            val dialog = SettingDialog(this)
            dialog.start(
                getString(R.string.dialog_reset_data_title),
                getString(R.string.dialog_reset_data_complete_button)
            )

            dialog.setOnOKClickedListener {
                viewModel.deleteAllData()
                /* TODO: 빈 메인화면 머지하고 startActivity 하자 */
//                startActivity(Intent(this, EmptyMainActivity::class.java))
                finish()
            }
        }
    }
}