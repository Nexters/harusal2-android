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
                val currentTime = Date().time
                val budgetDateList: ArrayList<Pair<Date, Date>> = arrayListOf()
                var tempBudgetEndDate = it.copy().endDate

                while (currentTime > tempBudgetEndDate.time) {
                    val startDate = tempBudgetEndDate.apply {
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

                    budgetDateList.add(Pair(startDate, endDate))
                    tempBudgetEndDate = endDate.clone() as Date
                }

                for ((start, end) in budgetDateList) {
                    budgetUseCase.insertBudget(it.budget, start, end)
                }
            }
        }
    }
}