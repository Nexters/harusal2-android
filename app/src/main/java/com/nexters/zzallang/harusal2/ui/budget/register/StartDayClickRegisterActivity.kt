package com.nexters.zzallang.harusal2.ui.budget.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.application.util.IntentUtils
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityRegisterDayClickBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartDayClickRegisterActivity : BaseActivity<ActivityRegisterDayClickBinding>() {
    override fun layoutRes(): Int = R.layout.activity_register_day_click
    override val viewModel: StartDayClickRegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override fun bindingView() {
        super.bindingView()

        binding.pickerDay.minValue = 1

        binding.pickerDay.maxValue = DateUtils.getLastDayOfMonthAfter(-1)

        binding.pickerDay.setOnValueChangedListener { _, _, newVal ->
            viewModel.pickedDayChanged(newVal)
        }

        binding.btnPrev.setOnClickListener {
            this.startActivity(Intent(this, StartDayDefaultRegisterActivity::class.java))
            this.finish()
        }

        binding.btnClose.setOnClickListener {
            this.startActivity(IntentUtils.getEmptyMainActivityIntent(this))
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
    }
}
