package com.nexters.zzallang.harusal2.ui.register

import android.content.Intent
import android.os.Bundle
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityRegisterDayDefaultBinding
import com.nexters.zzallang.harusal2.ui.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartDayDefaultRegisterActivity : BaseActivity<ActivityRegisterDayDefaultBinding>(){
    override fun layoutRes(): Int = R.layout.activity_register_day_default
    override val viewModel: StartDayDefaultRegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override fun bindingView() {
        super.bindingView()
        binding.tvDescription.text = viewModel.getDescription()
        binding.btnComplete.text = viewModel.getButtonText()

        binding.btnPrev.setOnClickListener {
            val intent = Intent(this, BudgetRegisterActivity::class.java)
            this.startActivity(intent)
        }

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, StartDayClickRegisterActivity::class.java)
            this.startActivity(intent)
        }

        binding.btnComplete.setOnClickListener {
            viewModel.saveBudgetDay()
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
        }

        binding.btnClose.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
        }
    }
}