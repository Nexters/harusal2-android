package com.nexters.zzallang.harusal2.ui.budget.edit

import android.os.Bundle
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityChangeDayBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartDayEditActivity : BaseActivity<ActivityChangeDayBinding>() {
    override fun layoutRes(): Int = R.layout.activity_change_day
    override val viewModel: StartDayEditViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override fun bindingView() {
        super.bindingView()
        binding.btnComplete.setOnClickListener {
            val dlg = StartDayEditDialog(this)
            dlg.setOnOKClickedListener {
                viewModel.save()
            }

            dlg.start(viewModel.initContent())
        }
    }
}