package com.nexters.zzallang.harusal2.ui.history

import androidx.lifecycle.MutableLiveData
import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.data.entity.Budget
import com.nexters.zzallang.harusal2.data.entity.Statement
import com.nexters.zzallang.harusal2.ui.history.model.*
import com.nexters.zzallang.harusal2.ui.main.SpendState
import com.nexters.zzallang.harusal2.usecase.BudgetUseCase
import com.nexters.zzallang.harusal2.usecase.StatementUseCase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HistoryViewModel(
    private val budgetUseCase: BudgetUseCase,
    private val statementUseCase: StatementUseCase
) : BaseViewModel() {
    private val todayDate = DateUtils.getTodayDate()
    private var recentBudget: Budget? = null
    var budgetList: List<Budget> = ArrayList()
    var cards: MutableLiveData<List<BaseHistoryRecyclerItem>> = MutableLiveData()
    var oneDayBudget: Int = 0

    suspend fun init() {
        budgetList = budgetUseCase.findAllDesc()
        recentBudget = budgetUseCase.findRecentBudget()
    }

    private fun calculateOnedayBudget(budget: Budget) {
        oneDayBudget = budget.budget / DateUtils.calculateDate(budget.startDate, budget.endDate)
    }

    private fun calculateState(budget: Budget, spentMoney: Int, isToday: Boolean): SpendState {
        if (spentMoney == 0) {
            return SpendState.FLEX
        }

        val endDate = when (isToday) {
            true -> Date()
            false -> budget.endDate
        }

        val safeMoney = this.oneDayBudget * DateUtils.calculateDate(budget.startDate, endDate)
        val spentMoney = -spentMoney
        return if (spentMoney < safeMoney * 0.9) {
            SpendState.FLEX
        } else if (spentMoney < safeMoney * 0.95) {
            SpendState.CLAP
        } else if (spentMoney < safeMoney * 1.04) {
            SpendState.DEFAULT
        } else if (spentMoney < safeMoney * 1.1) {
            SpendState.EMBARRASSED
        } else if (spentMoney < safeMoney * 1.15) {
            SpendState.CRY
        } else {
            SpendState.VOLCANO
        }
    }

    suspend fun createHistory(selectedBudget: Budget) {
        val recyclerItem = ArrayList<BaseHistoryRecyclerItem>()
        this.calculateOnedayBudget(selectedBudget)

        val histories =
            statementUseCase.findStatementByBudgetId(
                selectedBudget!!.id
            )
        var totalAmount = 0

        val formatter = SimpleDateFormat("yyyy.MM.dd")
        histories.forEach { statement: Statement ->
            statement.date = formatter.parse(formatter.format(statement.date))
            totalAmount += statement.amount
        }
        val mainCardName: String
        val selectDate: Date
        val isCurrent: Boolean

        when (selectedBudget.id) {
            recentBudget!!.id -> {
                mainCardName = "TODAY"
                selectDate = todayDate
                isCurrent = true
            }
            else -> {
                mainCardName = "RECENT"
                selectDate = selectedBudget.endDate
                isCurrent = false
            }
        }

        val groupingMainHistories = histories
            .filter { statement -> statement.date.date == selectDate.date }

        val groupingHistories = histories
            .filter { statement -> statement.date.date != selectDate.date }
            .groupBy { statement: Statement -> statement.date }
            .toList()
            .sortedByDescending { pair -> pair.first }

        recyclerItem.addAll(
            arrayListOf(
                HistoryInfo(
                    selectedBudget.budget + totalAmount,
                    DateUtils.startToEndToString(
                        selectedBudget.startDate,
                        selectedBudget.endDate
                    ),
                    this.calculateState(selectedBudget, totalAmount, isCurrent)
                ),
                HistoryTitle(mainCardName),
                createMainCard(
                    selectDate,
                    when (groupingMainHistories.isEmpty()) {
                        true -> null
                        else -> groupingMainHistories
                    }
                )
            )
        )

        if (groupingHistories.isNotEmpty()) {
            recyclerItem.add(HistoryTitle("DAILY"))
            recyclerItem.addAll(createRemainderCard(groupingHistories))
        }

        cards.postValue(recyclerItem)
    }

    private fun createMainCard(date: Date, todayList: List<Statement>?): HistoryCard {
        if (todayList == null) {
            return HistoryCard(day = date.date)
        }

        return createCard(date, todayList)
    }

    private fun createRemainderCard(historyGroup: List<Pair<Date, List<Statement>>>): List<HistoryCard> {
        val remainderHistoryCards = ArrayList<HistoryCard>()
        if (historyGroup.isEmpty()) {
            return remainderHistoryCards
        }

        historyGroup.forEach { pair: Pair<Date, List<Statement>> ->
            remainderHistoryCards.add(createCard(pair.first, pair.second))
        }

        return remainderHistoryCards
    }

    private fun createCard(date: Date, statements: List<Statement>): HistoryCard {
        var income = 0
        var spend = 0
        val histories = ArrayList<HistoryStatement>()
        statements.forEach { statement: Statement ->
            run {
                histories.add(
                    HistoryStatement(
                        statement.id,
                        statement.amount,
                        statement.summaryContent()
                    )
                )
                when {
                    statement.amount < 0 -> spend += statement.amount
                    else -> income += statement.amount
                }
            }
        }
        return HistoryCard(date.date, income, spend, histories)
    }
}