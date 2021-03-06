package com.nexters.zzallang.harusal2.ui.history

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityHistoryBinding
import com.nexters.zzallang.harusal2.ui.history.decoration.HistoryDecoration
import com.nexters.zzallang.harusal2.ui.history.menu.HistoryMenuAdapter
import com.nexters.zzallang.harusal2.ui.history.menu.HistoryMenuDialog
import com.nexters.zzallang.harusal2.ui.history.model.HistoryInfo
import com.nexters.zzallang.harusal2.ui.main.SpendState.Companion.getBackgroundColor
import com.nexters.zzallang.harusal2.ui.statement.register.AddStatementActivity
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HistoryActivity : BaseActivity<ActivityHistoryBinding>(),
    HistoryMenuDialog.ItemClickListener {
    override fun layoutRes(): Int = R.layout.activity_history
    override val viewModel: HistoryViewModel by viewModel()
    private var customDialog: HistoryMenuDialog? = null
    private val job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = 0
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override fun onResume() {
        super.onResume()

        refreshHistories()
    }

    override fun bindingView() {
        super.bindingView()

        binding.btnPrev.setOnClickListener {
            this.finish()
        }

        binding.btnAddStatement.setOnClickListener {
            this.startActivity(Intent(this, AddStatementActivity::class.java))
        }

        binding.rcvHistory.layoutManager = LinearLayoutManager(this@HistoryActivity)
        binding.rcvHistory.addItemDecoration(HistoryDecoration())
        binding.rcvHistory.setHasFixedSize(true)

        viewModel.cards.observe(this, Observer {
            val color: Int
            binding.rcvHistory.adapter =
                HistoryViewAdapter(this@HistoryActivity).apply {
                    val info = it[0] as HistoryInfo
                    color = getBackgroundColor(info.state)
                    binding.appbar.setBackgroundColor(
                        this@HistoryActivity.getColor(
                            color
                        )
                    )
                    clearHistoryStatement()
                    addStatements(it)
                }

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(color, null)
        })

        binding.layoutMenu.setOnClickListener {
            customDialog?.show()
        }

        refreshHistories()
    }

    private fun refreshHistories() {
        CoroutineScope(Dispatchers.Main + job).launch {
            withContext(Dispatchers.Default) { viewModel.init() }

            val historyMenuAdapter = HistoryMenuAdapter(
                this@HistoryActivity
            ).apply {
                clear()
                addBudget(viewModel.budgetList)
            }

            customDialog =
                HistoryMenuDialog(
                    this@HistoryActivity,
                    historyMenuAdapter
                )

            if (viewModel.budgetList.isNotEmpty()) {
                this@HistoryActivity.clickOnItem(0)
            }
        }
    }

    override fun clickOnItem(position: Int) {
        val budget = viewModel.budgetList[position]
        val newJob = Job()
        CoroutineScope(Dispatchers.Main + newJob).launch {
            viewModel.createHistory(budget)
        }

        binding.tvMenuTitle.text =
            DateUtils.startToEndToStringNoYear(budget.startDate, budget.endDate)

        customDialog?.dismiss()
    }
}