package com.nexters.zzallang.harusal2.ui.setting

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivitySettingBinding

class SettingActivity: BaseActivity<ActivitySettingBinding>() {
    override val viewModel: ViewModel
        get() = TODO("Not yet implemented")

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
    }
}