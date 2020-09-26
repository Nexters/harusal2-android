package com.nexters.zzallang.harusal2.usecase

import com.nexters.zzallang.harusal2.data.entity.Statement
import java.util.*

class StartDayEditUseCase(
    private val statementUseCase: StatementUseCase,
    private val budgetUseCase: BudgetUseCase
) {
    suspend fun saveBudgetDate() {
        val currentBudget = budgetUseCase.findRecentBudget()
        val todayDate = Date()
        val yesterdayDate = Date(todayDate.time + (1000 * 60 * 60 * 24 * -1))

        val statements = statementUseCase.findByDate(Date())
        currentBudget.endDate = yesterdayDate
        budgetUseCase.updateBudget(currentBudget)

        budgetUseCase.insertBudget(budget = currentBudget.budget, startDate = todayDate)

        statements.forEach { statement: Statement ->
            run {
                statement.budgetId = currentBudget.id
                statementUseCase.updateStatement(statement)
            }
        }
    }
}