package com.nexters.zzallang.harusal2.ui.splash

import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.data.entity.Budget
import com.nexters.zzallang.harusal2.usecase.BudgetUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class SplashViewModel(private val budgetUseCase: BudgetUseCase) : BaseViewModel() {
    fun renewBudgetDay() {
        launch {
            val budget: Budget? = withContext(coroutineContext) {
                budgetUseCase.findRecentBudget()
            }

            budget?.let {
                if (Date().time < budget.endDate.time) {
                    return@launch
                }

                val startDate = budget.endDate.apply {
                    date += 1
                    hours = 0
                    minutes = 0
                    seconds = 0
                }

                val endDate = (startDate.clone() as Date).apply {
                    month += 1
                    date -= 1
                    hours = 23
                    minutes = 59
                    seconds = 59
                }

                budgetUseCase.insertBudget(budget.budget, startDate, endDate)
            }
        }
    }
}