package com.nexters.zzallang.harusal2.ui.register

import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.usecase.BudgetUseCase

class StartDayDefaultRegisterViewModel(private val budgetUseCase: BudgetUseCase) : BaseViewModel() {
    private val day:Int = DateUtils.getDay()

    fun getDescription():String {
        return StringBuilder()
            .append("오늘은 ").append(day).append("일입니다.\n")
            .append("매달 ").append(day).append("일을 시작일자로\n")
            .append("지정할까요?")
            .toString()
    }

    fun getButtonText():String{
        return StringBuilder()
            .append(day).append("일로 시작하기")
            .toString()
    }

    fun saveBudgetDay() {
        budgetUseCase.setStartDate(day)
    }
}