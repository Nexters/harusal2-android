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


class AlarmSettingActivity: BaseActivity<ActivityAlarmSettingBinding>() {
    override val viewModel: AlarmSettingViewModel by viewModel()
    override fun layoutRes(): Int = R.layout.activity_alarm_setting

    private lateinit var alarmManager: AlarmManager
    /* TODO: pendingIntent 를 하나로 관리하기 때문에 하나만 꺼도
        전체가 취소되는 이슈가 생길 수 있다 해결 방안에 대해 모색하자.
        애초에 하나만 설정되고 나머지는 설정이 안될지도.. */
    private lateinit var pendingIntent: PendingIntent
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        val intent = Intent(this, Alarm::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        pref = getSharedPreferences("alarm_setting", Context.MODE_PRIVATE)

        initSwitchState()
    }

    override fun bindingView() {
        super.bindingView()

        binding.switchMain.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                binding.switchMorning.isChecked = false
                binding.switchAfternoon.isChecked = false
                binding.switchEvening.isChecked = false
                alarmManager.cancel(pendingIntent)
                val editor = pref.edit()
                editor.putBoolean("main", false)
                editor.putBoolean("morning", false)
                editor.putBoolean("afternoon", false)
                editor.putBoolean("evening", false).apply()
            } else {
                pref.edit().putBoolean("main", true).apply()
            }

            binding.layoutContents.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        binding.ivClose.setOnClickListener {
            finish()
        }

        /* TODO: 바디 크기가 안맞아서 고쳐야함. */
        binding.switchMorning.apply {
            setTrackResource(R.drawable.track_setting_switch)
            setThumbResource(R.drawable.thumb_setting_switch)
            setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    alarmRegister(AlertTime.MORNING)
                    pref.edit().putBoolean("morning", true).apply()
                } else {
                    alarmManager.cancel(pendingIntent)
                    pref.edit().putBoolean("morning", false).apply()
                }
            }
        }
        binding.switchAfternoon.apply {
            setTrackResource(R.drawable.track_setting_switch)
            setThumbResource(R.drawable.thumb_setting_switch)
            setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    alarmRegister(AlertTime.AFTERNOON)
                    pref.edit().putBoolean("afternoon", true).apply()
                } else {
                    alarmManager.cancel(pendingIntent)
                    pref.edit().putBoolean("afternoon", false).apply()
                }
            }
        }
        binding.switchEvening.apply{
            setTrackResource(R.drawable.track_setting_switch)
            setThumbResource(R.drawable.thumb_setting_switch)
            setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    alarmRegister(AlertTime.EVENING)
                    pref.edit().putBoolean("evening", true).apply()
                } else {
                    alarmManager.cancel(pendingIntent)
                    pref.edit().putBoolean("evening", false).apply()
                }
            }
        }
    }

    private fun initSwitchState() {
        binding.switchMain.isChecked = pref.getBoolean("main", false)
        binding.switchMorning.isChecked = pref.getBoolean("morning", false)
        binding.switchAfternoon.isChecked = pref.getBoolean("afternoon", false)
        binding.switchEvening.isChecked = pref.getBoolean("evening", false)
    }


    private fun alarmRegister(alertTime: AlertTime) {
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, when (alertTime) {
                AlertTime.MORNING -> 9
                AlertTime.AFTERNOON -> 12
                else -> 20
            })
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }
}