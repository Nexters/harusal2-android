package com.nexters.zzallang.harusal2.ui.budget.edit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.usecase.BudgetUseCase

class StartDayChangeViewModel(private val budgetUseCase: BudgetUseCase) : BaseViewModel() {
    private val todayDay = DateUtils.getDay()
    private val _description = MutableLiveData("")

    val description: LiveData<String> get() = _description

    val pickedDay = MutableLiveData(15)

    init {
        reset()
    }

    fun saveBudgetDay() {
        budgetUseCase.setStartDate(pickedDay.value?:todayDay)
    }

    fun pickedDayChanged(pickedDay: Int) {
        this.pickedDay.postValue(pickedDay)
        this.setDescription(pickedDay)
    }

    fun reset(){
        val savedDay = budgetUseCase.getStartDate()
        pickedDay.postValue(savedDay)
        setDescription(savedDay)
    }

    private fun setDescription(pickedDay: Int) {
        val thisMonth = DateUtils.getMonth()
        val nextMonth = when (pickedDay) {
            1 -> thisMonth
            else -> thisMonth + 1
        }
        val endDay = when (pickedDay) {
            1 -> DateUtils.getLastDayOfMonth()
            else -> pickedDay - 1
        }

        _description.postValue(
            StringBuilder("생활비 사용기간 ")
                .append(thisMonth).append(".").append(pickedDay).append(" - ")
                .append(nextMonth).append(".").append(endDay)
                .toString()
        )
    }
}