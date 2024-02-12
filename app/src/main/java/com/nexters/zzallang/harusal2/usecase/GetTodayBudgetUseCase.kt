package com.nexters.zzallang.harusal2.usecase

import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.data.entity.Budget
import com.nexters.zzallang.harusal2.data.entity.Statement
import com.nexters.zzallang.harusal2.data.repository.BudgetRepository
import com.nexters.zzallang.harusal2.data.repository.StatementRepository
import java.time.LocalDate

class GetTodayBudgetUseCase(
    private val budgetRepository: BudgetRepository,
    private val statementRepository: StatementRepository
) {
    suspend fun getTodayBudget(): Int {
        var spentMoney = 0
        val now = LocalDate.now()

        val budget: Budget = budgetRepository.findRecentBudget()

        val statements: List<Statement> = statementRepository.findStatementByBudgetId(budget.id)

        statements.filter { it.date.isBefore(now) }.forEach { spentMoney += it.amount }

        val remainDate = DateUtils.calculateDate(now, budget.endDate)

        return (budget.budget + spentMoney) / remainDate
    }
}
