package com.nexters.zzallang.harusal2.ui.budget.edit

import com.nexters.zzallang.harusal2.data.entity.Statement
import com.nexters.zzallang.harusal2.usecase.BudgetUseCase
import com.nexters.zzallang.harusal2.usecase.StatementUseCase
import java.util.*

class StartDayEditUseCase(
    private val statementUseCase: StatementUseCase,
    private val budgetUseCase: BudgetUseCase
) {
    fun initContent(): String {
        val todayDate = Date().date
        return StringBuilder().append("오늘은 ").append(todayDate).append("일입니다.\n")
            .append(todayDate).append("일로 시작일을 수정합니다.\n")
            .append("수정 후에는 새롭게 한달 기준으로\n 하루 생활비가 책정됩니다.").toString()
    }

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