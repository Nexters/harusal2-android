package com.nexters.zzallang.harusal2.usecase

import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.data.repository.BudgetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import java.time.LocalDate

class GetRemainDayUseCase(private val budgetRepository: BudgetRepository) {
    private val job = Job()

    suspend fun getRemainDay(): Int {
        val budget = withContext(Dispatchers.IO + job) {
            budgetRepository.findRecentBudget()
        }

        return DateUtils.calculateDate(LocalDate.now(), budget.endDate)
    }
}