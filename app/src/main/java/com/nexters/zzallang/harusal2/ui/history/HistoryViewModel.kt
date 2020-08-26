package com.nexters.zzallang.harusal2.ui.history

import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.data.entity.Budget
import com.nexters.zzallang.harusal2.data.entity.Statement
import com.nexters.zzallang.harusal2.ui.history.model.*
import com.nexters.zzallang.harusal2.usecase.BudgetUseCase
import com.nexters.zzallang.harusal2.usecase.StatementUseCase
import java.util.*
import kotlin.collections.ArrayList

class HistoryViewModel(
    private val budgetUseCase: BudgetUseCase,
    private val statementUseCase: StatementUseCase
) : BaseViewModel() {
    private val todayDate = DateUtils.getTodayDate()

    suspend fun createHistory(selectedBudget: Budget): ArrayList<BaseHistoryRecyclerItem> {
        val recyclerItem = ArrayList<BaseHistoryRecyclerItem>()
        val recentBudget = budgetUseCase.findRecentBudget()
        val histories =
            statementUseCase.findByStartDateBetweenEndDate(
                selectedBudget.startDate,
                selectedBudget.endDate
            )
        var totalAmount = 0

        histories.forEach { statement: Statement -> totalAmount += statement.amount }

        val selectDate = when (selectedBudget) {
            recentBudget -> todayDate
            else -> selectedBudget.endDate
        }

        val groupingMainHistories = histories
            .filter { statement -> statement.date == selectDate }
            .groupBy { statement: Statement -> statement.date }
            .toList()

        val groupingHistories = histories
            .filter { statement -> statement.date != selectDate }
            .groupBy { statement: Statement -> statement.date }
            .toList()
            .sortedByDescending { pair -> pair.first }

        recyclerItem.addAll(
            arrayListOf(
                HistoryInfo(
                    selectedBudget.budget - totalAmount,
                    DateUtils.startToEndToString(
                        selectedBudget.startDate,
                        selectedBudget.endDate
                    )
                ),
                HistoryTitle("TODAY"),
                createTodayCard(groupingMainHistories[0].second),
                HistoryTitle("DAILY")
            )
        )
        recyclerItem.addAll(createRemainderCard(groupingHistories))
        return recyclerItem
    }

    private fun createTodayCard(todayList: List<Statement>?): HistoryCard {
        if (todayList == null) {
            return HistoryCard(day = todayDate.date)
        }

        return createCard(todayDate, todayList)
    }

    private fun createRemainderCard(historyGroup: List<Pair<Date, List<Statement>>>?): List<HistoryCard> {
        val remainderHistoryCards = ArrayList<HistoryCard>()
        if (historyGroup == null) {
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
                histories.add(HistoryStatement(statement.amount, statement.summaryContent()))
                if (statement.amount < 0) {
                    spend += statement.amount
                } else {
                    income += statement.amount
                }
            }
        }
        return HistoryCard(date.date, income.toString(), spend.toString(), histories)
    }
}