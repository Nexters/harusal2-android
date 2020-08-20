package com.nexters.zzallang.harusal2.ui.onBoarding

import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityOnboardingBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnBoardingActivity: BaseActivity<ActivityOnboardingBinding>(){
    override fun layoutRes(): Int = R.layout.activity_onboarding
    override val viewModel: OnBoardingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override fun bindingView() {
        super.bindingView()
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(OnBoardingFragment.newInstance("first fragment"))
        adapter.addFragment(OnBoardingFragment.newInstance("second fragment"))

        binding.pagerOnboarding.adapter = adapter
    }
}