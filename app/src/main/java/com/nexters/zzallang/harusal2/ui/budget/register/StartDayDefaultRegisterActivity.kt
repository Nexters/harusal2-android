package com.nexters.zzallang.harusal2.ui.budget.register

import android.content.Intent
import android.os.Bundle
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityRegisterDayDefaultBinding
import com.nexters.zzallang.harusal2.ui.main.EmptyMainActivity
import com.nexters.zzallang.harusal2.ui.main.MainActivity
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
        binding.tvNoticeDay.text = viewModel.getDescription()
        binding.tvDescription.text = viewModel.getDay().toString()

        binding.btnPrev.setOnClickListener {
            this.startActivity(Intent(this, BudgetRegisterActivity::class.java))
            this.finish()
        }

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, StartDayClickRegisterActivity::class.java)
            intent.putExtra("budget", this.intent.getStringExtra("budget"))
            this.startActivity(intent)
            this.finish()
        }

        binding.btnComplete.setOnClickListener {
            viewModel.saveBudgetDay(this.intent.getStringExtra("budget").toInt())
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
            this.finish()
        }

        binding.btnClose.setOnClickListener {
            this.startActivity(Intent(this, EmptyMainActivity::class.java))
            this.finish()
        }
    }
}