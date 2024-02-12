package com.nexters.zzallang.harusal2.usecase

import com.nexters.zzallang.harusal2.data.entity.Budget
import com.nexters.zzallang.harusal2.data.entity.Statement
import com.nexters.zzallang.harusal2.data.repository.BudgetRepository
import com.nexters.zzallang.harusal2.data.repository.StatementRepository
import java.time.LocalDate

class GetRemainMoneyUseCase(
    private val budgetRepository: BudgetRepository,
    private val statementRepository: StatementRepository
) {
    suspend fun getRemainMoney(todayBudget: Int): Int {
        val budget: Budget = budgetRepository.findRecentBudget()
        val statements: List<Statement> = statementRepository.findStatementByBudgetId(budget.id)

        val todayStatements: List<Statement> =
            statements.filter { statement -> statement.date.dayOfMonth == LocalDate.now().dayOfMonth }

        var remainMoney: Int = todayBudget
        for (todayStatement in todayStatements) {
            remainMoney += todayStatement.amount
        }

        return remainMoney
    }

    suspend fun getRemainMoney(): Int {
        val budget = budgetRepository.findRecentBudget()
        val statements = statementRepository.findStatementByBudgetId(budget.id)

        val spentMoneyForMonth = statements.sumOf { it.amount }

        return budget.budget + spentMoneyForMonth
    }
}
