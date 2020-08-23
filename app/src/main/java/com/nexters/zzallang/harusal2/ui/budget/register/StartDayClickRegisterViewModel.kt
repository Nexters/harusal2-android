package com.nexters.zzallang.harusal2.ui.budget.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.application.util.DateUtils.getDay
import com.nexters.zzallang.harusal2.application.util.DateUtils.getLastDayOfMonth
import com.nexters.zzallang.harusal2.application.util.DateUtils.getMonth
import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.usecase.BudgetUseCase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StartDayClickRegisterViewModel(private val budgetUseCase: BudgetUseCase) : BaseViewModel() {
    private val todayDay = getDay()
    private val _description = MutableLiveData("")
    val description: LiveData<String> get() = _description
    val pickedDay = MutableLiveData(15)

    init {
        pickedDay.postValue(todayDay)
        setDescription(todayDay)
    }

    fun saveBudgetDay(budget: Int) {
        GlobalScope.launch {
            budgetUseCase.insertBudget(
                budget,
                DateUtils.getTodayDate()
            )
        }
    }

    fun pickedDayChanged(pickedDay: Int) {
        this.pickedDay.postValue(pickedDay)
        this.setDescription(pickedDay)
    }

    private fun setDescription(pickedDay: Int) {
        val thisMonth = getMonth()

        val nextMonth = when (pickedDay) {
            1 -> thisMonth
            else -> thisMonth + 1
        }
        val nextDay = when (pickedDay) {
            1 -> getLastDayOfMonth()
            else -> pickedDay - 1
        }

        _description.postValue(
            StringBuilder("생활비 사용기간은 ")
                .append(thisMonth).append(".").append(pickedDay)
                .append(" - ")
                .append(nextMonth).append(".").append(nextDay)
                .toString()
        )
    }
}