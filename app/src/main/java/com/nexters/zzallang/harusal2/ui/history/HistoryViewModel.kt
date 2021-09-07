package com.nexters.zzallang.harusal2.ui.history

import androidx.lifecycle.MutableLiveData
import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.data.entity.Budget
import com.nexters.zzallang.harusal2.data.entity.Statement
import com.nexters.zzallang.harusal2.ui.history.model.*
import com.nexters.zzallang.harusal2.constant.SpendState
import com.nexters.zzallang.harusal2.usecase.BudgetUseCase
import com.nexters.zzallang.harusal2.usecase.StatementUseCase
import java.time.LocalDate

class HistoryViewModel(
    private val budgetUseCase: BudgetUseCase,
    private val statementUseCase: StatementUseCase
) : BaseViewModel() {
    private val todayDate = LocalDate.now()
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
            true -> LocalDate.now()
            false -> budget.endDate
        }

        val safeMoney = this.oneDayBudget * DateUtils.calculateDate(budget.startDate, endDate)
        val spentMoney = -spentMoney
        return when {
            spentMoney > safeMoney * 1.8 -> SpendState.VOLCANO
            spentMoney > safeMoney * 1.4 -> SpendState.CRY
            spentMoney > safeMoney * 1.1 -> SpendState.EMBARRASSED
            spentMoney > safeMoney * 0.3 -> SpendState.CLAP
            else -> SpendState.FLEX
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

        histories.forEach { statement: Statement ->
            totalAmount += statement.amount
        }

        val mainCardName: String
        val selectDate: LocalDate
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
            .filter { statement -> statement.date.dayOfMonth == selectDate.dayOfMonth }

        val groupingHistories = histories
            .filter { statement -> statement.date.dayOfMonth != selectDate.dayOfMonth }
            .groupBy { statement: Statement -> statement.date }
            .toList()
            .sortedByDescending { pair -> pair.first }

        recyclerItem.addAll(
            arrayListOf(
                HistoryInfo(
                    selectedBudget.budget,
                    totalAmount,
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

    private fun createMainCard(date: LocalDate, todayList: List<Statement>?): HistoryCard {
        if (todayList == null) {
            return HistoryCard(day = date.dayOfMonth)
        }

        return createCard(date, todayList)
    }

    private fun createRemainderCard(historyGroup: List<Pair<LocalDate, List<Statement>>>): List<HistoryCard> {
        val remainderHistoryCards = ArrayList<HistoryCard>()
        if (historyGroup.isEmpty()) {
            return remainderHistoryCards
        }

        historyGroup.forEach { pair: Pair<LocalDate, List<Statement>> ->
            remainderHistoryCards.add(createCard(pair.first, pair.second))
        }

        return remainderHistoryCards
    }

    private fun createCard(date: LocalDate, statements: List<Statement>): HistoryCard {
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
        return HistoryCard(date.dayOfMonth, income, spend, histories)
    }
}