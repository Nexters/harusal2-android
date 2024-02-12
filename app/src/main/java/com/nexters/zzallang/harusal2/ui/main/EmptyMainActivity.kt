package com.nexters.zzallang.harusal2.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieDrawable
import com.nexters.zzallang.harusal2.databinding.ActivityEmptyMainBinding
import com.nexters.zzallang.harusal2.ui.budget.register.BudgetRegisterActivity

class EmptyMainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityEmptyMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmptyMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        bindingView()
    }

    private fun bindingView() {
        binding.ivEmoji.run {
            playAnimation()
            repeatCount = LottieDrawable.INFINITE
        }

        binding.btnSettingBudget.setOnClickListener {
            startActivity(Intent(this, BudgetRegisterActivity::class.java))
        }
    }
}
