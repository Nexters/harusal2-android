package com.nexters.zzallang.harusal2.ui.setting

import androidx.lifecycle.MutableLiveData
import com.nexters.zzallang.harusal2.base.BaseViewModel

class AlarmSettingViewModel: BaseViewModel() {
    /* TODO: sharedPreferences 랑 같이 고민해보자 */
    val mainSwitch = MutableLiveData<Boolean>(false)
}