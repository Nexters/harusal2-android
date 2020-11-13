package com.nexters.zzallang.harusal2.ui.setting

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.util.IntentUtils
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.data.entity.Budget
import com.nexters.zzallang.harusal2.databinding.ActivitySettingBinding
import com.nexters.zzallang.harusal2.ui.budget.edit.BudgetEditActivity
import com.nexters.zzallang.harusal2.ui.setting.receiver.Alarm
import com.nexters.zzallang.harusal2.ui.setting.view.SettingDialog
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class SettingActivity : BaseActivity<ActivitySettingBinding>() {
    override val viewModel: SettingViewModel by viewModel()
    override fun layoutRes(): Int = R.layout.activity_setting
    private lateinit var alarmManager: AlarmManager
    private lateinit var pref: SharedPreferences

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
            val dialog =
                SettingDialog(this)
            val job = Job()
            CoroutineScope(Dispatchers.Main + job).launch {
                var recentBudget: Budget
                withContext(Dispatchers.Main + job) {
                    recentBudget = viewModel.findBudget()
                }

                val todayDate: Int = Date().date

                if (recentBudget.startDate.date == todayDate) {
                    Toast.makeText(
                        this@SettingActivity,
                        R.string.dialog_edit_budget_date_error,
                        Toast.LENGTH_SHORT
                    ).show()
                    return@launch
                }

                dialog.setOnOKClickedListener {
                    viewModel.editBudgetDate()
                }

                dialog.start(
                    getString(R.string.dialog_edit_budget_date_title, todayDate, todayDate),
                    getString(R.string.dialog_edit_budget_date_complete_button, todayDate)
                )
            }


        }

        binding.btnResetData.setOnClickListener {
            val dialog =
                SettingDialog(this)
            dialog.start(
                getString(R.string.dialog_reset_data_title),
                getString(R.string.dialog_reset_data_complete_button)
            )

            dialog.setOnOKClickedListener {
                viewModel.deleteAllData()
                startActivity(IntentUtils.getEmptyMainActivityIntent(this))
                cancelAllAlarm()
                finish()
            }
        }
    }

    private fun cancelAllAlarm() {
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        pref = getSharedPreferences("alarm_setting", Context.MODE_PRIVATE)

        cancelAlarm(AlarmSettingActivity.MORNING_INTENT_CODE)
        cancelAlarm(AlarmSettingActivity.AFTERNOON_INTENT_CODE)
        cancelAlarm(AlarmSettingActivity.EVENING_INTENT_CODE)

        val editor = pref.edit()
        editor.putBoolean("main", false)
        editor.putBoolean("morning", false)
        editor.putBoolean("afternoon", false)
        editor.putBoolean("evening", false).apply()
    }

    private fun cancelAlarm(intentCode: Int) {
        alarmManager.cancel(getPendingIntent(intentCode))
    }

    private fun getPendingIntent(code: Int): PendingIntent {
        val intent = Intent(this, Alarm::class.java)
        val bundle = Bundle()
        bundle.putInt(AlarmSettingActivity.ALARM_INTENT_NAME, code)
        intent.putExtras(bundle)

        return PendingIntent.getBroadcast(
            this,
            code,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}