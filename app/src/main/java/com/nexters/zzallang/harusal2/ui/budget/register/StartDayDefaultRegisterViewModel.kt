package com.nexters.zzallang.harusal2.ui.budget.register

import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.usecase.BudgetUseCase
import kotlinx.coroutines.launch
import java.time.LocalDate

class StartDayDefaultRegisterViewModel(private val budgetUseCase: BudgetUseCase) : BaseViewModel() {
    private val todayDate = LocalDate.now()

    fun getDescription(): String {
        return StringBuilder()
            .append("매달 ").append(getDay()).append("일을\n")
            .append("시작일자로 할까요?")
            .toString()
    }

    fun getDay(): Int {
        return todayDate.dayOfMonth
    }

    fun saveBudgetDay(budget: Int) {
        launch {
            budgetUseCase.insertBudget(
                budget,
                todayDate
            )
        }
    }
}