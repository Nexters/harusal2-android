package com.nexters.zzallang.harusal2.ui.bridge

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityBridgeBinding
import com.nexters.zzallang.harusal2.ui.budget.register.BudgetRegisterActivity
import com.nexters.zzallang.harusal2.ui.budget.register.StartDayClickRegisterActivity
import com.nexters.zzallang.harusal2.ui.statement.edit.StatementActivity
import com.nexters.zzallang.harusal2.ui.statement.register.AddStatementActivity
import com.nexters.zzallang.harusal2.ui.history.HistoryActivity
import com.nexters.zzallang.harusal2.ui.budget.register.StartDayDefaultRegisterActivity
import com.nexters.zzallang.harusal2.ui.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class BridgeActivity : BaseActivity<ActivityBridgeBinding>() {
    override val viewModel: ViewModel by viewModel()

    override fun layoutRes(): Int = R.layout.activity_bridge

    override fun bindingView() {
        super.bindingView()
        binding.recyclerBridge.apply {
            val bridgeAdapter = BridgeAdapter(this@BridgeActivity)
            bridgeAdapter.activityList = arrayListOf(
                MainActivity::class.java,
                HistoryActivity::class.java,
                BudgetRegisterActivity::class.java,
                StartDayDefaultRegisterActivity::class.java,
                StartDayClickRegisterActivity::class.java,
                AddStatementActivity::class.java,
                StatementActivity::class.java
            )
            adapter = bridgeAdapter
            layoutManager = LinearLayoutManager(this@BridgeActivity)
            setHasFixedSize(true)
        }
    }
}