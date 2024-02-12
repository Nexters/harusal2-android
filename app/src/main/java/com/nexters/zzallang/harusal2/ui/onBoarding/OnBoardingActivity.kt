package com.nexters.zzallang.harusal2.ui.onBoarding

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.util.IntentUtils
import com.nexters.zzallang.harusal2.databinding.ActivityOnboardingBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnBoardingActivity: AppCompatActivity(){
    private lateinit var binding: ActivityOnboardingBinding
    private val viewModel: OnBoardingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(OnBoardingFragment.newInstance(getString(R.string.title_onboarding1), getString(R.string.text_onboarding1), 1))
        adapter.addFragment(OnBoardingFragment.newInstance(getString(R.string.title_onboarding2), getString(R.string.text_onboarding2), 2))
        adapter.addFragment(OnBoardingFragment.newInstance(getString(R.string.title_onboarding3), getString(R.string.text_onboarding3), 3))

        binding.pagerOnboarding.adapter = adapter
        binding.tabOnboarding.setupWithViewPager(binding.pagerOnboarding)

        binding.btnOnboardingClose.setOnClickListener {
            startActivity(IntentUtils.getEmptyMainActivityIntent(this))
            finish()
        }
    }
}
