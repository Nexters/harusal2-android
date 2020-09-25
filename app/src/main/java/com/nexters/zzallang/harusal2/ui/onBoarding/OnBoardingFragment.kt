package com.nexters.zzallang.harusal2.ui.onBoarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.lottie.LottieDrawable
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseFragment
import com.nexters.zzallang.harusal2.databinding.FragmentOnboardingBinding
import com.nexters.zzallang.harusal2.ui.budget.register.BudgetRegisterActivity
import com.nexters.zzallang.harusal2.ui.main.MainActivity

class OnBoardingFragment: BaseFragment<FragmentOnboardingBinding>() {
    companion object {
        fun newInstance(title: String, text: String, order: Int): OnBoardingFragment {
            val fragment = OnBoardingFragment()
            val bundle = Bundle()
            bundle.putString("title", title)
            bundle.putString("text", text)
            bundle.putInt("order", order)
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
        viewModel.setTitle(requireArguments().getString("title") ?: "")
        viewModel.setText(requireArguments().getString("text") ?: "")

        binding.lottieOnboarding.setAnimation("onboarding"+requireArguments().getInt("order")+".json")
        binding.lottieOnboarding.playAnimation()
        binding.lottieOnboarding.repeatCount = LottieDrawable.INFINITE

        if (requireArguments().getInt("order") == 3){
            binding.btnOnboardingStart.visibility = View.VISIBLE
        }

        binding.btnOnboardingStart.setOnClickListener {
            activity?.finish()
            val intent = Intent(context, BudgetRegisterActivity::class.java)
            startActivity(intent)
        }
    }
}