package com.nexters.zzallang.harusal2.usecase

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

    suspend fun getRemainMoney(livingExpenses: Int): Int {
        val budget = withContext(Dispatchers.IO + job) {
            budgetRepository.findRecentBudget()
        }

        val statements = withContext(Dispatchers.IO + job) {
            statementRepository.findStatementByBudgetId(budget.id)
        }

        val todayStatements =
            statements.filter { statement -> statement.date.dayOfMonth == LocalDate.now().dayOfMonth }

        var remainMoney = livingExpenses
        for (item in todayStatements) {
            remainMoney += item.amount
        }

        return remainMoney
    }
}