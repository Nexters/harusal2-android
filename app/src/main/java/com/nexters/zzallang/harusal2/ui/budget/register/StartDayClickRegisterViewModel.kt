package com.nexters.zzallang.harusal2.ui.budget.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.usecase.BudgetUseCase
import kotlinx.coroutines.launch
import java.time.LocalDate

class StartDayClickRegisterViewModel(private val budgetUseCase: BudgetUseCase) : BaseViewModel() {
    private val todayDate = LocalDate.now()
    private val _description = MutableLiveData("")
    val description: LiveData<String> get() = _description
    val pickedDate = MutableLiveData(15)

    private lateinit var startDate: LocalDate
    private lateinit var endDate: LocalDate

    init {
        pickedDate.postValue(todayDate.monthValue)
        setDescription(todayDate.monthValue)
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
        startDate = when {
            pickedDate <= todayDate.dayOfMonth -> {
                LocalDate.now()
            }
            else -> {
                todayDate.minusMonths(1)
            }
        }

        startDate = startDate.withDayOfMonth(pickedDate)
        endDate = DateUtils.getBudgetEndDate(startDate)

        _description.postValue(
            StringBuilder("생활비 사용기간은 ")
                .append(startDate.monthValue).append(".").append(startDate.dayOfMonth)
                .append(" - ")
                .append(endDate.monthValue).append(".").append(endDate.dayOfMonth)
                .toString()
        )
    }
}