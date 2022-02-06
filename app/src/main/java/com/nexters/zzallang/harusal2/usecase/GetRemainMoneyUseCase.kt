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

    suspend fun getRemainMoney(todayBudget: Int): Int {
        val budget = withContext(Dispatchers.IO + job) {
            budgetRepository.findRecentBudget()
        }

        val statements = withContext(Dispatchers.IO + job) {
            statementRepository.findStatementByBudgetId(budget.id)
        }

        val todayStatements =
            statements.filter { statement -> statement.date.dayOfMonth == LocalDate.now().dayOfMonth }

        var remainMoney = todayBudget
        for (todayStatement in todayStatements) {
            remainMoney += todayStatement.amount
        }

        return remainMoney
    }

    suspend fun getRemainMoney(): Int {
        val budget = withContext(Dispatchers.IO + job) {
            budgetRepository.findRecentBudget()
        }

        val statements = withContext(Dispatchers.IO + job) {
            statementRepository.findStatementByBudgetId(budget.id)
        }

        val spentMoneyForMonth = statements.sumBy { it.amount }

        return budget.budget + spentMoneyForMonth
    }
}