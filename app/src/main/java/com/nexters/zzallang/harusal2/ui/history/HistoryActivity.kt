package com.nexters.zzallang.harusal2.ui.history

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.application.util.MoneyUtils
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.data.entity.Budget
import com.nexters.zzallang.harusal2.databinding.ActivityHistoryBinding
import com.nexters.zzallang.harusal2.ui.history.decoration.HistoryDecoration
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HistoryActivity : BaseActivity<ActivityHistoryBinding>(),
    HistoryMenuAdaptor.RecyclerViewItemClickListener {
    override fun layoutRes(): Int = R.layout.activity_history
    override val viewModel: HistoryViewModel by viewModel()
    private var customDialog: CustomViewDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override fun bindingView() {
        super.bindingView()

        binding.btnPrev.setOnClickListener {
            this.finish()
        }

        val job = Job()
        CoroutineScope(Dispatchers.Main + job).launch {
            withContext(Dispatchers.Default) { viewModel.init() }

            binding.layoutMenu.setOnClickListener {
                val historyMenuAdaptor = HistoryMenuAdaptor(viewModel.budgetList, this@HistoryActivity)
                customDialog = CustomViewDialog(this@HistoryActivity, historyMenuAdaptor)
                customDialog!!.show()
            }
        }

        binding.rcvHistory.layoutManager = LinearLayoutManager(this@HistoryActivity)
        binding.rcvHistory.addItemDecoration(HistoryDecoration())
        binding.rcvHistory.setHasFixedSize(true)
        viewModel.cards.observe(this, Observer {
            binding.rcvHistory.adapter = HistoryViewAdaptor(this@HistoryActivity).apply {
                clearHistoryStatement()
                addStatements(it)
            }
        })
    }

    override fun clickOnItem(budget: Budget) {
        val newJob = Job()
        CoroutineScope(Dispatchers.Main + newJob).launch {
            viewModel.createHistory(budget)
        }

        binding.tvMenuTitle.text = DateUtils.startToEndToStringNoYear(budget.startDate, budget.endDate)

        if (customDialog != null) {
            customDialog!!.dismiss()
        }
    }
}