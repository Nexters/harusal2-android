package com.nexters.zzallang.harusal2.usecase

import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.data.repository.BudgetRepository
import com.nexters.zzallang.harusal2.data.repository.StatementRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import java.time.LocalDate

class GetRemainMoneyUseCase(
    private val budgetRepository: BudgetRepository,
    private val statementRepository: StatementRepository
) {
    private val job = Job()

    suspend fun getRemainMoney(): Int {
        var spentMoney = 0
        val now = LocalDate.now()

        val budget = withContext(Dispatchers.IO + job) {
            budgetRepository.findRecentBudget()
        }

        val statements = withContext(Dispatchers.IO + job) {
            statementRepository.findStatementByBudgetId(budget.id)
        }

        statements.filter { it.date.isBefore(now) }.forEach { spentMoney += it.amount }

        val remainDate = DateUtils.calculateDate(now, budget.endDate)
        val livingExpenses = (budget.budget + spentMoney) / remainDate
        val todayStatements =
            statements.filter { statement -> statement.date.dayOfMonth == LocalDate.now().dayOfMonth }

        var remainMoney = livingExpenses
        for (item in todayStatements) {
            remainMoney += item.amount
        }

        return remainMoney
    }
}