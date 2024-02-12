package com.nexters.zzallang.harusal2.usecase

import com.nexters.zzallang.harusal2.data.entity.Statement
import java.time.LocalDate

class StartDayEditUseCase(
    private val statementUseCase: StatementUseCase,
    private val budgetUseCase: BudgetUseCase
) {
    suspend fun saveBudgetDate() {
        var currentBudget = budgetUseCase.findRecentBudget()
        val todayDate = LocalDate.now()
        val yesterday = todayDate.minusDays(1)

        val statements = statementUseCase.findByDate(todayDate)
        currentBudget.endDate = yesterday
        budgetUseCase.updateBudget(currentBudget)

        budgetUseCase.insertBudget(budget = currentBudget.budget, startDate = todayDate)
        currentBudget = budgetUseCase.findRecentBudget()

        statements.forEach { statement: Statement ->
            run {
                statement.budgetId = currentBudget.id
                statementUseCase.updateStatement(statement)
            }
        }
    }
}
