package com.nexters.zzallang.harusal2.ui.budget.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.zzallang.harusal2.application.util.DateUtils.getDay
import com.nexters.zzallang.harusal2.application.util.DateUtils.getLastDayOfMonth
import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.usecase.BudgetUseCase
import kotlinx.coroutines.launch
import java.util.*

class StartDayClickRegisterViewModel(private val budgetUseCase: BudgetUseCase) : BaseViewModel() {
    private val todayDay = getDay()
    private val _description = MutableLiveData("")
    val description: LiveData<String> get() = _description
    val pickedDate = MutableLiveData(15)

    private lateinit var startDate: Date
    private lateinit var endDate: Date

    init {
        pickedDate.postValue(todayDay)
        setDescription(todayDay)
    }

    fun saveBudgetDay(budget: Int) {
        launch {
            budgetUseCase.insertBudget(
                budget,
                startDate = startDate,
                endDate = endDate
            )
        }
    }

    fun pickedDayChanged(pickedDate: Int) {
        this.pickedDate.postValue(pickedDate)
        this.setDescription(pickedDate)
    }

    private fun setDescription(pickedDate: Int) {
        val todayDate = Date()

        val startCalendar = when {
            pickedDate <= todayDate.date -> {
                Calendar.getInstance()
            }
            else -> {
                val cal = Calendar.getInstance()
                cal.add(Calendar.MONTH, -1)
                cal
            }
        }
        startCalendar.set(Calendar.DATE, pickedDate)
        startDate = Date(startCalendar.timeInMillis).apply {
            hours = 0
            minutes = 0
            seconds = 0
        }

        val endCalendar = when {
            pickedDate == 1 -> {
                Calendar.getInstance()
            }
            pickedDate <= todayDate.date -> {
                val cal = Calendar.getInstance()
                cal.add(Calendar.MONTH, 1)
                cal
            }
            else -> {
                Calendar.getInstance()
            }
        }

        when (pickedDate) {
            1 -> endCalendar.set(Calendar.DATE, getLastDayOfMonth())
            else -> endCalendar.set(Calendar.DATE, pickedDate - 1)
        }
        endDate = Date(endCalendar.timeInMillis).apply {
            hours = 23
            minutes = 59
            seconds = 59
        }

        _description.postValue(
            StringBuilder("생활비 사용기간은 ")
                .append(startDate.month + 1).append(".").append(startDate.date)
                .append(" - ")
                .append(endDate.month + 1).append(".").append(endDate.date)
                .toString()
        )
    }
}