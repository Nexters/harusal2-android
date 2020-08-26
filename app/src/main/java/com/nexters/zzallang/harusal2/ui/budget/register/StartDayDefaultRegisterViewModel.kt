package com.nexters.zzallang.harusal2.ui.budget.register

import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.usecase.BudgetUseCase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StartDayDefaultRegisterViewModel(private val budgetUseCase: BudgetUseCase) : BaseViewModel() {
    private val day: Int = DateUtils.getDay()

    fun getDescription(): String {
        return StringBuilder()
            .append("매달 ").append(day).append("일을\n")
            .append("시작일자로 할까요?")
            .toString()
    }

    fun getDay(): Int {
        return day
    }

    fun saveBudgetDay(budget: Int) {
        val todayDate = DateUtils.getTodayDate()

        GlobalScope.launch {
            budgetUseCase.insertBudget(
                budget,
                todayDate
            )
        }
    }
}