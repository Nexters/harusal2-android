package com.nexters.zzallang.harusal2.ui.setting

import androidx.lifecycle.ViewModel
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityAlarmSettingBinding

class AlarmSettingActivity: BaseActivity<ActivityAlarmSettingBinding>() {
    override val viewModel: ViewModel
        get() = TODO("Not yet implemented")

    override fun layoutRes(): Int = R.layout.activity_alarm_setting

    override fun bindingView() {
        super.bindingView()

        binding.ivClose.setOnClickListener {
            finish()
        }
    }
}