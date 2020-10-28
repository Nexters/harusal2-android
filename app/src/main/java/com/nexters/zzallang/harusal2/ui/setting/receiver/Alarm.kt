package com.nexters.zzallang.harusal2.ui.setting.receiver

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.App
import com.nexters.zzallang.harusal2.application.util.IntentUtils
import com.nexters.zzallang.harusal2.ui.setting.AlarmSettingActivity

class Alarm: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        pushAlert(intent?.extras?.getInt(AlarmSettingActivity.ALARM_INTENT_NAME)?:0)
    }

    private fun pushAlert(intentCode: Int) {
        val notificationBuilder = NotificationCompat.Builder(App.app, "insert_push_alert")
            .setContentTitle("하루살이")
            .setContentText("오늘의 기록을 남겨보세요!")
            .setSmallIcon(R.drawable.android_appicon_36)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(
                PendingIntent.getActivity(
                    App.app, intentCode,
                IntentUtils.getMainActivityIntent(App.app), PendingIntent.FLAG_UPDATE_CURRENT)
            )
            .setAutoCancel(true)

        val notificationManager = App.app.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(intentCode, notificationBuilder.build())
    }
}