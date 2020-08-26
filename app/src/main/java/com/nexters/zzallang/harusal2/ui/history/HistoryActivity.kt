package com.nexters.zzallang.harusal2.ui.history

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityHistoryBinding
import com.nexters.zzallang.harusal2.ui.history.decoration.HistoryDecoration
import com.nexters.zzallang.harusal2.ui.history.model.HistoryCard
import com.nexters.zzallang.harusal2.ui.history.model.HistoryInfo
import com.nexters.zzallang.harusal2.ui.history.model.HistoryStatement
import com.nexters.zzallang.harusal2.ui.history.model.HistoryTitle
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

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

        binding.btnPrev.setOnClickListener {
            this.finish()
        }
        binding.rcvHistory.layoutManager = LinearLayoutManager(this)
        binding.rcvHistory.addItemDecoration(HistoryDecoration())

        binding.rcvHistory.adapter = HistoryViewAdaptor(this).apply {
            clearHistoryStatement()
            addStatements(
                arrayListOf(
                    HistoryInfo(300000, "2020.07.01 - 2020.07.31"),
                    HistoryTitle("TODAY"),
                    HistoryCard(
                        21, "1000", "2000", listOf(
                            HistoryStatement(1000, "어케하냐"),
                            HistoryStatement(2000, "어케하지?")
                        )
                    ),
                    HistoryTitle("DAILY"),
                    HistoryCard(19, "1000", "2000", listOf(
                        HistoryStatement(1000, "어케하냐"),
                        HistoryStatement(2000, "어케하지?"),
                        HistoryStatement(3000, "왜 안되지?")
                    )),
                    HistoryCard(18, "1000", "2000", null),
                    HistoryCard(17, "1000", "2000", null),
                    HistoryCard(16, "1000", "2000", null),
                    HistoryCard(15, "1000", "2000", null),
                    HistoryCard(14, "1000", "2000", null)
                )
            )
        }
        binding.rcvHistory.setHasFixedSize(true)
    }
}