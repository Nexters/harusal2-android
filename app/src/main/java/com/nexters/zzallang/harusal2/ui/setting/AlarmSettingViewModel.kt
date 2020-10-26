package com.nexters.zzallang.harusal2.ui.setting

import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.ui.setting.type.AlertTime
import java.util.*

class AlarmSettingViewModel: BaseViewModel() {
    fun getAlarmCalender(alertTime: AlertTime): Calendar =
        Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(
                Calendar.HOUR_OF_DAY,
                when (alertTime) {
                    AlertTime.MORNING -> 9
                    AlertTime.AFTERNOON -> 12
                    else -> 20
                }
            )
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)

            if (before(Calendar.getInstance())) {
                add(Calendar.DATE, 1)
            }
        }
}