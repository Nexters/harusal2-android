package com.nexters.zzallang.harusal2.ui.onBoarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.lottie.LottieDrawable
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseFragment
import com.nexters.zzallang.harusal2.databinding.FragmentOnboardingBinding

class OnBoardingFragment: BaseFragment<FragmentOnboardingBinding>() {
    companion object {
        fun newInstance(title: String, text: String, num: String): OnBoardingFragment {
            val fragment = OnBoardingFragment()
            val bundle = Bundle()
            bundle.putString("title", title)
            bundle.putString("text", text)
            bundle.putString("num", num)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun layoutRes(): Int = R.layout.fragment_onboarding
    override val viewModel: OnBoardingViewModel = OnBoardingViewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this@OnBoardingFragment
        return binding.root
    }

    override fun bindingView() {
        super.bindingView()
        viewModel.setTitle(requireArguments().getString("title") ?: "null")
        viewModel.setText(requireArguments().getString("text") ?: "null")

        binding.lottieOnboarding.setAnimation("onboarding"+requireArguments().getString("num")+".json")
        binding.lottieOnboarding.playAnimation()
        binding.lottieOnboarding.repeatCount = LottieDrawable.INFINITE
    }
}