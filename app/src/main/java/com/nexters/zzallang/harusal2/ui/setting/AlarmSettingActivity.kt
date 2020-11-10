package com.nexters.zzallang.harusal2.ui.setting

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityAlarmSettingBinding
import com.nexters.zzallang.harusal2.ui.setting.receiver.Alarm
import com.nexters.zzallang.harusal2.ui.setting.type.AlertTime
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class AlarmSettingActivity : BaseActivity<ActivityAlarmSettingBinding>() {
    companion object {
        const val ALARM_INTENT_NAME = "ALARM_INTENT"
        private const val MORNING_PREF_NAME = "morning"
        private const val AFTERNOON_PREF_NAME = "afternoon"
        private const val EVENING_PREF_NAME = "evening"
        const val MORNING_INTENT_CODE = 0
        const val AFTERNOON_INTENT_CODE = 1
        const val EVENING_INTENT_CODE = 2
    }

    override val viewModel: AlarmSettingViewModel by viewModel()
    override fun layoutRes(): Int = R.layout.activity_alarm_setting
    private lateinit var alarmManager: AlarmManager
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        pref = getSharedPreferences("alarm_setting", Context.MODE_PRIVATE)

        initSwitchState()
    }

    override fun bindingView() {
        super.bindingView()

        binding.ivClose.setOnClickListener {
            finish()
        }

        /* TODO: 바디 크기가 안맞아서 고쳐야함. */
        binding.switchMain.apply {
            setTrackResource(R.drawable.track_setting_switch)
            setThumbResource(R.drawable.thumb_setting_switch)
            setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked) {
                    cancelAllAlarm()
                } else {
                    pref.edit().putBoolean("main", true).apply()
                }
                binding.layoutContents.visibility = if (isChecked) View.VISIBLE else View.GONE
            }
        }

        binding.switchMorning.apply {
            setTrackResource(R.drawable.track_setting_switch)
            setThumbResource(R.drawable.thumb_setting_switch)
            setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    alarmRegister(AlertTime.MORNING, MORNING_INTENT_CODE)
                    setAlarmPref(MORNING_PREF_NAME, true)
                } else {
                    alarmManager.cancel(getPendingIntent(MORNING_INTENT_CODE))
                    setAlarmPref(MORNING_PREF_NAME, false)
                }
            }
        }

        binding.switchAfternoon.apply {
            setTrackResource(R.drawable.track_setting_switch)
            setThumbResource(R.drawable.thumb_setting_switch)
            setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    alarmRegister(AlertTime.AFTERNOON, AFTERNOON_INTENT_CODE)
                    setAlarmPref(AFTERNOON_PREF_NAME, true)
                } else {
                    alarmManager.cancel(getPendingIntent(AFTERNOON_INTENT_CODE))
                    setAlarmPref(AFTERNOON_PREF_NAME, false)
                }
            }
        }

        binding.switchEvening.apply {
            setTrackResource(R.drawable.track_setting_switch)
            setThumbResource(R.drawable.thumb_setting_switch)
            setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    alarmRegister(AlertTime.EVENING, EVENING_INTENT_CODE)
                    setAlarmPref(EVENING_PREF_NAME, true)
                } else {
                    alarmManager.cancel(getPendingIntent(EVENING_INTENT_CODE))
                    setAlarmPref(EVENING_PREF_NAME, false)
                }
            }
        }
    }

    private fun setAlarmPref(key: String, value: Boolean) {
        pref.edit().putBoolean(key, value).apply()
    }

    private fun initSwitchState() {
        binding.switchMain.isChecked = pref.getBoolean("main", false)
        binding.switchMorning.isChecked = pref.getBoolean("morning", false)
        binding.switchAfternoon.isChecked = pref.getBoolean("afternoon", false)
        binding.switchEvening.isChecked = pref.getBoolean("evening", false)
    }

    private fun alarmRegister(alertTime: AlertTime, intentCode: Int) {
        val calendar: Calendar = viewModel.getAlarmCalender(alertTime)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            getPendingIntent(intentCode)
        )
    }

    private fun getPendingIntent(code: Int): PendingIntent {
        val intent = Intent(this, Alarm::class.java)
        val bundle = Bundle()
        bundle.putInt(ALARM_INTENT_NAME, code)
        intent.putExtras(bundle)

        return PendingIntent.getBroadcast(
            this,
            code,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun cancelAllAlarm() {
        binding.switchMorning.isChecked = false
        binding.switchAfternoon.isChecked = false
        binding.switchEvening.isChecked = false

        cancelAlarm(MORNING_INTENT_CODE)
        cancelAlarm(AFTERNOON_INTENT_CODE)
        cancelAlarm(EVENING_INTENT_CODE)

        val editor = pref.edit()
        editor.putBoolean("main", false)
        editor.putBoolean("morning", false)
        editor.putBoolean("afternoon", false)
        editor.putBoolean("evening", false).apply()
    }

    private fun cancelAlarm(intentCode: Int) {
        alarmManager.cancel(getPendingIntent(intentCode))
    }
}