package com.nexters.zzallang.harusal2.ui.main

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.airbnb.lottie.LottieDrawable
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityEmptyMainBinding
import com.nexters.zzallang.harusal2.ui.budget.register.BudgetRegisterActivity
import com.nexters.zzallang.harusal2.ui.setting.AlarmSettingViewModel

class EmptyMainActivity: BaseActivity<ActivityEmptyMainBinding>() {
    // 안써서 베이스뷰모델 대충 넣은 상태..
    override val viewModel: ViewModel = AlarmSettingViewModel()

    override fun layoutRes(): Int = R.layout.activity_empty_main

    override fun bindingView() {
        super.bindingView()

        binding.ivEmoji.run {
            playAnimation()
            repeatCount = LottieDrawable.INFINITE
        }

        binding.btnSettingBudget.setOnClickListener {
            startActivity(Intent(this, BudgetRegisterActivity::class.java))
        }
    }
}
