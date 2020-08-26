package com.nexters.zzallang.harusal2.ui.history

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.data.entity.Budget
import com.nexters.zzallang.harusal2.databinding.ActivityHistoryBinding
import com.nexters.zzallang.harusal2.ui.history.decoration.HistoryDecoration
import com.nexters.zzallang.harusal2.ui.history.model.HistoryCard
import com.nexters.zzallang.harusal2.ui.history.model.HistoryInfo
import com.nexters.zzallang.harusal2.ui.history.model.HistoryStatement
import com.nexters.zzallang.harusal2.ui.history.model.HistoryTitle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class HistoryActivity : BaseActivity<ActivityHistoryBinding>() {
    override fun layoutRes(): Int = R.layout.activity_history
    override val viewModel: HistoryViewModel by viewModel()
    private val job = Job()
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

        binding.rcvHistory.layoutManager = LinearLayoutManager(this@HistoryActivity)
        binding.rcvHistory.addItemDecoration(HistoryDecoration())
        binding.rcvHistory.adapter = HistoryViewAdaptor(this@HistoryActivity)

        CoroutineScope(Dispatchers.Main + job).launch {
            viewModel.initBudgetList()
            val strArray = ArrayList<String>()

            //TODO : 이거 삭제
            strArray.add("A")
            strArray.add("B")
            strArray.add("C")

            //Spinner 설정
            viewModel.budgetList.forEach { budget: Budget ->
                strArray.add(
                    DateUtils.startToEndToString(
                        budget.startDate,
                        budget.endDate
                    )
                )
            }
            val spinnerAdaptor = ArrayAdapter(
                this@HistoryActivity,
                R.layout.support_simple_spinner_dropdown_item,
                strArray
            )
            binding.spinnerBudget.adapter = spinnerAdaptor
            binding.spinnerBudget.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?, view: View?,
                    i: Int,
                    l: Long
                ) {
                    //TODO : Toast 삭제
                    with(binding.rcvHistory.adapter as HistoryViewAdaptor){
                        launch {
                            clearHistoryStatement()
                            addStatements(viewModel.createHistory(viewModel.budgetList.get(i)))
                        }
                    }
                    Toast.makeText(
                        this@HistoryActivity, i.toString() + " " + l.toString() + "가 선택되었습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }


            //History List 설정
            binding.rcvHistory.adapter = HistoryViewAdaptor(this@HistoryActivity).apply {
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
                        HistoryCard(
                            19, "1000", "2000", listOf(
                                HistoryStatement(1000, "어케하냐"),
                                HistoryStatement(2000, "어케하지?"),
                                HistoryStatement(3000, "왜 안되지?")
                            )
                        ),
                        HistoryCard(18, "1000", "2000", null),
                        HistoryCard(17, "1000", "2000", null),
                        HistoryCard(16, "1000", "2000", null),
                        HistoryCard(15, "1000", "2000", null),
                        HistoryCard(14, "1000", "2000", null)
                    )
                )
            }
        }
        binding.rcvHistory.setHasFixedSize(true)
    }
}