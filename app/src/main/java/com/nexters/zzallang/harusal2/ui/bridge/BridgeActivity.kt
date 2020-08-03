package com.nexters.zzallang.harusal2.ui.bridge

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityBridgeBinding
import com.nexters.zzallang.harusal2.ui.MainActivity
import com.nexters.zzallang.harusal2.ui.register.BudgetDayRegisterActivity
import com.nexters.zzallang.harusal2.ui.register.BudgetRegisterActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class BridgeActivity: BaseActivity<ActivityBridgeBinding>() {
    override val viewModel: ViewModel by viewModel()

    override fun layoutRes(): Int = R.layout.activity_bridge

    override fun bindingView() {
        super.bindingView()
        binding.recyclerBridge.apply {
            val bridgeAdapter = BridgeAdapter(this@BridgeActivity)
            bridgeAdapter.activityList = arrayListOf(
                MainActivity::class.java,
                BudgetRegisterActivity::class.java,
                BudgetDayRegisterActivity::class.java
            )
            adapter = bridgeAdapter
            layoutManager = LinearLayoutManager(this@BridgeActivity)
            setHasFixedSize(true)
        }
    }
}