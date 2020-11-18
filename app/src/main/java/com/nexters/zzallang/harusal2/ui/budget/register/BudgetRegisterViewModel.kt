package com.nexters.zzallang.harusal2.ui.budget.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.application.util.MoneyUtils
import com.nexters.zzallang.harusal2.base.BaseViewModel

class BudgetRegisterViewModel() : BaseViewModel() {
    val budget = MutableLiveData("")

    private val _hangeulBudget = MutableLiveData("")
    private val _averageBudget = MutableLiveData("")
    val hangeulBudget: LiveData<String> get() = _hangeulBudget
    val averageBudget: LiveData<String> get() = _averageBudget
    private val duration: Int = DateUtils.getLastDayOfMonth()

    fun textClear() {
        _hangeulBudget.postValue("0원")
        _averageBudget.postValue("0원")
        return
    }

    fun budgetChanged(text: String) {
        if (text == "") {
            textClear()
            return
        }

        val inputBudget = text.toLong()

        _averageBudget.postValue((inputBudget / duration).toString() + "원")
        hangeulBudget(inputBudget)
    }

    fun hangeulBudget(budget: Long) {
        _hangeulBudget.postValue(MoneyUtils.convertString(budget))
    }
}