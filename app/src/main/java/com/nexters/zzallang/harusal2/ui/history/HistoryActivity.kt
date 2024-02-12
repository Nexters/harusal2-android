package com.nexters.zzallang.harusal2.ui.history

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.constant.Constants
import com.nexters.zzallang.harusal2.constant.SpendState.Companion.getBackgroundColor
import com.nexters.zzallang.harusal2.databinding.ActivityHistoryBinding
import com.nexters.zzallang.harusal2.ui.history.decoration.HistoryDecoration
import com.nexters.zzallang.harusal2.ui.history.menu.HistoryMenuAdapter
import com.nexters.zzallang.harusal2.ui.history.menu.HistoryMenuDialog
import com.nexters.zzallang.harusal2.ui.history.model.HistoryInfo
import com.nexters.zzallang.harusal2.ui.statement.register.AddStatementActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel


class HistoryActivity : AppCompatActivity(), HistoryMenuDialog.ItemClickListener {
    private lateinit var binding: ActivityHistoryBinding
    private val viewModel: HistoryViewModel by viewModel()
    private var customDialog: HistoryMenuDialog? = null
    private val job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = 0
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)

        initViews()
    }

    override fun onResume() {
        super.onResume()

        refreshHistories()
    }

    private fun initViews() {
        binding.btnPrev.setOnClickListener {
            this.finish()
        }

        binding.btnAddStatement.setOnClickListener {
            val intent = Intent(it.context, AddStatementActivity::class.java)
            intent.putExtra(Constants.BEFORE_ACTIVITY_KEY,"HISTORY")

            it.context.startActivity(intent)        }

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
