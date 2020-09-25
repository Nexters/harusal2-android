package com.nexters.zzallang.harusal2.ui.setting

import android.os.Bundle
import android.view.View
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityAlarmSettingBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlarmSettingActivity: BaseActivity<ActivityAlarmSettingBinding>() {
    override val viewModel: AlarmSettingViewModel by viewModel()

    override fun layoutRes(): Int = R.layout.activity_alarm_setting

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        binding.switchMain.setOnCheckedChangeListener { _, isChecked ->
            run {
                binding.layoutContents.visibility = if (isChecked) View.VISIBLE else View.GONE
            }
        }
    }

    override fun bindingView() {
        super.bindingView()

        binding.ivClose.setOnClickListener {
            finish()
        }
    }
}