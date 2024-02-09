package com.nexters.zzallang.harusal2.usecase

import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.data.entity.Budget
import com.nexters.zzallang.harusal2.data.repository.BudgetRepository
import java.time.LocalDate


class BudgetUseCase(private val budgetRepository: BudgetRepository) {
    suspend fun updateBudget(budget: Budget) {
        budgetRepository.update(budget)
    }

    suspend fun insertBudget(budget: Int, startDate: LocalDate) {
        budgetRepository.insertBudget(
            Budget(
                startDate = startDate,
                budget = budget,
                endDate = DateUtils.getBudgetEndDate(startDate)
            )
        )
    }

    suspend fun insertBudget(budget: Int, startDate: LocalDate, endDate: LocalDate) {
        budgetRepository.insertBudget(
            Budget(
                startDate = startDate,
                budget = budget,
                endDate = endDate
            )
        )
    }

    suspend fun findRecentBudget(): Budget =
        budgetRepository.findRecentBudget()

    suspend fun findAll(): List<Budget> = budgetRepository.findAll()

    suspend fun findAllDesc(): List<Budget> = budgetRepository.findAllDesc()

    suspend fun deleteAllBudget() = budgetRepository.deleteAllBudget()
}
