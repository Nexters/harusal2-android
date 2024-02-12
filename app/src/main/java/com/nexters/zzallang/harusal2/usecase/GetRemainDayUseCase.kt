package com.nexters.zzallang.harusal2.usecase

import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.data.entity.Budget
import com.nexters.zzallang.harusal2.data.repository.BudgetRepository
import java.time.LocalDate

class GetRemainDayUseCase(private val budgetRepository: BudgetRepository) {

    suspend fun getRemainDay(): Int {
        val budget: Budget = budgetRepository.findRecentBudget()

        return DateUtils.calculateDate(LocalDate.now(), budget.endDate)
    }
}
