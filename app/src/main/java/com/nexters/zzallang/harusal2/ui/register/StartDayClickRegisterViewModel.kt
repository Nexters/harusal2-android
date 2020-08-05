package com.nexters.zzallang.harusal2.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.zzallang.harusal2.application.util.DateUtils.getLastDayOfMonth
import com.nexters.zzallang.harusal2.application.util.DateUtils.getMonth
import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.usecase.BudgetUseCase

class StartDayClickRegisterViewModel(private val budgetUseCase: BudgetUseCase) : BaseViewModel() {
    val pickedDay = MutableLiveData(15)

    private val _description = MutableLiveData("")
    val description: LiveData<String> get() = _description

    fun saveBudgetDay() {
        budgetUseCase.setStartDate(pickedDay.value!!)
    }

    //TODO - 양방향 무한 호출 개선
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
        val endDay = when(pickedDay){
            1 -> getLastDayOfMonth()
            else -> pickedDay - 1
        }

        _description.postValue(
            StringBuilder("생활비 사용기간은\n")
                .append(thisMonth).append(".").append(pickedDay).append(" ~ ")
                .append(nextMonth).append(".").append(endDay).append("입니다.")
                .toString()
        )
    }
}