package com.nexters.zzallang.harusal2.ui.onBoarding

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.preference.PreferenceManager
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityOnboardingBinding
import com.nexters.zzallang.harusal2.ui.main.EmptyMainActivity
import com.nexters.zzallang.harusal2.ui.main.MainActivity
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
        adapter.addFragment(OnBoardingFragment.newInstance(getString(R.string.title_onboarding1), getString(R.string.text_onboarding1), 1))
        adapter.addFragment(OnBoardingFragment.newInstance(getString(R.string.title_onboarding2), getString(R.string.text_onboarding2), 2))
        adapter.addFragment(OnBoardingFragment.newInstance(getString(R.string.title_onboarding3), getString(R.string.text_onboarding3), 3))

        binding.pagerOnboarding.adapter = adapter
        binding.tabOnboarding.setupWithViewPager(binding.pagerOnboarding)

        binding.btnOnboardingClose.setOnClickListener {
            finish()
            val intent = Intent(this, EmptyMainActivity::class.java)
            startActivity(intent)
        }
    }
}