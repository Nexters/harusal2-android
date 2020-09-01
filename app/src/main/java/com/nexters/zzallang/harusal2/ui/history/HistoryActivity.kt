package com.nexters.zzallang.harusal2.ui.history

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityHistoryBinding
import com.nexters.zzallang.harusal2.ui.history.decoration.HistoryDecoration
import com.nexters.zzallang.harusal2.ui.history.model.HistoryInfo
import com.nexters.zzallang.harusal2.ui.main.MainActivity
import com.nexters.zzallang.harusal2.ui.main.SpendState.Companion.getBackgroundColor
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HistoryActivity : BaseActivity<ActivityHistoryBinding>(),
    HistoryMenuDialog.ItemClickListener {
    override fun layoutRes(): Int = R.layout.activity_history
    override val viewModel: HistoryViewModel by viewModel()
    private var customDialog: HistoryMenuDialog? = null

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

        binding.btnAddStatement.setOnClickListener {
            //TODO : 내역 등록으로 이동
            this.startActivity(Intent(this, MainActivity::class.java))
        }

        val job = Job()
        CoroutineScope(Dispatchers.Main + job).launch {
            withContext(Dispatchers.Default) { viewModel.init() }
            val historyMenuAdapter = HistoryMenuAdapter(this@HistoryActivity).apply {
                clear()
                addBudget(viewModel.budgetList)
            }

            customDialog = HistoryMenuDialog(this@HistoryActivity, historyMenuAdapter)

            if (viewModel.budgetList.isNotEmpty()) {
                this@HistoryActivity.clickOnItem(0)
            }

            binding.layoutMenu.setOnClickListener {
                customDialog!!.show()
            }
        }

        binding.rcvHistory.layoutManager = LinearLayoutManager(this@HistoryActivity)
        binding.rcvHistory.addItemDecoration(HistoryDecoration())
        binding.rcvHistory.setHasFixedSize(true)
        viewModel.cards.observe(this, Observer {
            binding.rcvHistory.adapter =
                HistoryViewAdaptor(this@HistoryActivity, viewModel.oneDayBudget).apply {
                    val info = it[0] as HistoryInfo
                    binding.appbar.setBackgroundColor(
                        this@HistoryActivity.getColor(
                            getBackgroundColor(info.state)
                        )
                    )
                    clearHistoryStatement()
                    addStatements(it)
                }
        })
    }

    override fun clickOnItem(position: Int) {
        val budget = viewModel.budgetList[position]
        val newJob = Job()
        CoroutineScope(Dispatchers.Main + newJob).launch {
            viewModel.createHistory(budget)
        }

        binding.tvMenuTitle.text =
            DateUtils.startToEndToStringNoYear(budget.startDate, budget.endDate)
        if (customDialog != null) {
            customDialog!!.dismiss()
        }
    }
}