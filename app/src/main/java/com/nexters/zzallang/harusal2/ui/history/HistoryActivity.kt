package com.nexters.zzallang.harusal2.ui.history

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityHistoryBinding
import com.nexters.zzallang.harusal2.ui.history.decoration.HistoryDecoration
import com.nexters.zzallang.harusal2.ui.history.model.HistoryStatement
import com.nexters.zzallang.harusal2.ui.history.model.HistoryTitle
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryActivity : BaseActivity<ActivityHistoryBinding>() {
    override fun layoutRes(): Int = R.layout.activity_history
    override val viewModel: HistoryViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override fun bindingView() {
        super.bindingView()

        binding.rcvHistory.layoutManager = LinearLayoutManager(this)
        binding.rcvHistory.addItemDecoration(HistoryDecoration())
        binding.rcvHistory.adapter = HistoryViewAdaptor(this).apply {
            clearHistoryStatement()
            addStatements(arrayListOf(
                HistoryTitle("TODAY"),
                HistoryStatement(21, "1000", "2000"),
                HistoryTitle("DAILY"),
                HistoryStatement(19, "1000", "2000"),
                HistoryStatement(18, "1000", "2000"),
                HistoryStatement(17, "1000", "2000"),
                HistoryStatement(16, "1000", "2000"),
                HistoryStatement(15, "1000", "2000"),
                HistoryStatement(14, "1000", "2000")
            ))
        }
        binding.rcvHistory.setHasFixedSize(true)
    }
}