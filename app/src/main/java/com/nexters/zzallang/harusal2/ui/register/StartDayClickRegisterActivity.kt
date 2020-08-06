package com.nexters.zzallang.harusal2.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityRegisterDayClickBinding
import com.nexters.zzallang.harusal2.ui.MainActivity
import com.shawnlin.numberpicker.NumberPicker
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.String

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
        binding.pickerDay.maxValue = DateUtils.getLastDayOfMonth()

        binding.pickerDay.setOnValueChangedListener({ picker, oldVal, newVal ->
            viewModel.pickedDayChanged(newVal)
        })

        binding.btnPrev.setOnClickListener { this.startActivity(Intent(this, StartDayDefaultRegisterActivity::class.java)) }
        binding.btnClose.setOnClickListener { this.startActivity(Intent(this, MainActivity::class.java)) }
        binding.btnComplete.setOnClickListener {
            viewModel.saveBudgetDay()
            this.startActivity(Intent(this, MainActivity::class.java))
        }
    }
}