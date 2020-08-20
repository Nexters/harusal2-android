package com.nexters.zzallang.harusal2.ui.onBoarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseFragment
import com.nexters.zzallang.harusal2.databinding.FragmentOnboardingBinding

class OnBoardingFragment: BaseFragment<FragmentOnboardingBinding>() {
    companion object {
        fun newInstance(message: String): OnBoardingFragment {
            val fragment = OnBoardingFragment()
            val bundle = Bundle(1)
            bundle.putString("test", message)
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
        viewModel.setText(requireArguments().getString("test") ?: "null")
    }
}