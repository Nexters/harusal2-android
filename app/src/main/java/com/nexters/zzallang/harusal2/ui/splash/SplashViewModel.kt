package com.nexters.zzallang.harusal2.ui.splash

import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.data.entity.Budget
import com.nexters.zzallang.harusal2.usecase.BudgetUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.util.*

class SplashViewModel(private val budgetUseCase: BudgetUseCase) : BaseViewModel() {
    fun renewBudgetDay() {
        launch {
            val budget: Budget? = withContext(coroutineContext) {
                budgetUseCase.findRecentBudget()
            }

            budget?.let {
                val todayDate = LocalDate.now()
                val budgetDateList: ArrayList<Pair<LocalDate, LocalDate>> = arrayListOf()
                var tempBudgetEndDate = it.copy().endDate

                while (todayDate.isAfter(tempBudgetEndDate)) {
                    val startDate = tempBudgetEndDate.plusDays(1)
                    var endDate = startDate.plusMonths(1).plusDays(1)

                    budgetDateList.add(Pair(startDate, endDate))
                    tempBudgetEndDate = endDate
                }

                for ((start, end) in budgetDateList) {
                    budgetUseCase.insertBudget(it.budget, start, end)
                }
            }
        }
    }
}