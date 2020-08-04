package com.nexters.zzallang.harusal2.ui.register

import android.app.Activity
import android.content.Intent
import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.usecase.BudgetUseCase
import java.util.*

class BudgetDayRegisterViewModel(private val budgetUseCase: BudgetUseCase) : BaseViewModel() {
    private val day:Int
    init {
        day = getDay()
    }

    fun getDescription():String {
        return StringBuilder()
            .append("오늘은 ").append(day).append("일입니다.\n")
            .append("매달 ").append(day).append("일을 시작일자로\n")
            .append("지정할까요?")
            .toString()
    }

    fun getButtonText():String{
        return StringBuilder()
            .append(getDay()).append("일로 시작하기")
            .toString()
    }

    private fun getDay(): Int {
        val date = Date()
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.DAY_OF_MONTH)
    }

    fun saveBudgetDay() {
        budgetUseCase.setStartDate(day)
    }
}