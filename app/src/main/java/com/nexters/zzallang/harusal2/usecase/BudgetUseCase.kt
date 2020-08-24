package com.nexters.zzallang.harusal2.usecase

import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.data.entity.Budget
import com.nexters.zzallang.harusal2.data.repository.BudgetRepository
import java.util.*


class BudgetUseCase(private val budgetRepository: BudgetRepository) {
    suspend fun updateBudget(budget: Budget){
        budgetRepository.update(budget)
    }

    suspend fun insertBudget(budget: Int, startDate: Date) {
        budgetRepository.insertBudget(
            Budget(
                startDate = startDate,
                budget = budget,
                endDate = DateUtils.endDate(startDate)
            )
        )
    }

    suspend fun insertBudget(budget: Int, startDate: Date, endDate: Date) {
        budgetRepository.insertBudget(
            Budget(
                startDate = startDate,
                budget = budget,
                endDate = endDate
            )
        )
    }

    suspend fun findRecentBudget(): Budget? =
        budgetRepository.findRecentBudget()
}
