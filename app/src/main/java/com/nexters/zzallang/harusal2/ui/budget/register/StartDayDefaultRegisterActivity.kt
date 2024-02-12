package com.nexters.zzallang.harusal2.ui.budget.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nexters.zzallang.harusal2.application.util.IntentUtils
import com.nexters.zzallang.harusal2.databinding.ActivityRegisterDayDefaultBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartDayDefaultRegisterActivity : AppCompatActivity(){
    private lateinit var binding: ActivityRegisterDayDefaultBinding
    private val viewModel: StartDayDefaultRegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterDayDefaultBinding.inflate(layoutInflater)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
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
            val budget = this.intent.getStringExtra("budget")
            if (budget != null) {
                viewModel.saveBudgetDay(budget.toInt())

                this.startActivity(IntentUtils.getMainActivityIntent(this))
                this.finish()
            } else {
                Toast.makeText(this, "정상적으로 저장되지 않았습니다. 다시 실행해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnClose.setOnClickListener {
            this.startActivity(IntentUtils.getEmptyMainActivityIntent(this))
            this.finish()
        }
    }
}
