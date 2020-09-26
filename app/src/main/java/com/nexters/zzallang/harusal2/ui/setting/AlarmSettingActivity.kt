package com.nexters.zzallang.harusal2.ui.setting

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityAlarmSettingBinding
import com.nexters.zzallang.harusal2.ui.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlarmSettingActivity: BaseActivity<ActivityAlarmSettingBinding>() {
    override val viewModel: AlarmSettingViewModel by viewModel()

    override fun layoutRes(): Int = R.layout.activity_alarm_setting

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override fun bindingView() {
        super.bindingView()

        binding.switchMain.setOnCheckedChangeListener { _, isChecked ->
            run {
                binding.layoutContents.visibility = if (isChecked) View.VISIBLE else View.GONE
            }
        }

        binding.ivClose.setOnClickListener {
            finish()
            pushAlert()
        }

        /* TODO: 바디 크기가 안맞아서 고쳐야함. */
        binding.switchMorning.apply {
            setTrackResource(R.drawable.track_setting_switch)
            setThumbResource(R.drawable.thumb_setting_switch)
        }
        binding.switchAfternoon.apply {
            setTrackResource(R.drawable.track_setting_switch)
            setThumbResource(R.drawable.thumb_setting_switch)
        }
        binding.switchEvening.apply{
            setTrackResource(R.drawable.track_setting_switch)
            setThumbResource(R.drawable.thumb_setting_switch)
        }
    }

    /* TODO: Timer에 맞춰서 동작하게 하자 */
    private fun pushAlert() {
        val notificationBuilder = NotificationCompat.Builder(this, "insert_push_alert")
            .setContentTitle("하루살이")
            .setContentText("오늘의 기록을 남겨보세요!")
            .setSmallIcon(R.drawable.android_appicon_36)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(PendingIntent.getActivity(this, 0,
                Intent(this, MainActivity::class.java), 0))
            .setAutoCancel(true)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(1, notificationBuilder.build())
    }
}