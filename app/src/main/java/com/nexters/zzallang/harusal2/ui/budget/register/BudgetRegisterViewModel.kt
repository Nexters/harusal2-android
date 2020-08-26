package com.nexters.zzallang.harusal2.ui.budget.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.zzallang.harusal2.application.util.MoneyUtils
import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.usecase.BudgetUseCase


class BudgetRegisterViewModel(private val budgetUseCase: BudgetUseCase) : BaseViewModel() {
    val budget = MutableLiveData("")

    private val _hangeulBudget = MutableLiveData("")
    private val _averageBudget = MutableLiveData("")
    val hangeulBudget: LiveData<String> get() = _hangeulBudget
    val averageBudget: LiveData<String> get() = _averageBudget

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

        val inputBudget: Long = java.lang.Long.valueOf(text)

        _averageBudget.postValue((inputBudget / 30L).toString() + "원")
        hangeulBudget(inputBudget)
    }

    fun hangeulBudget(budget: Long) {
        _hangeulBudget.postValue(MoneyUtils.convertString(budget))
    }

    fun saveBudget() {
        val savedBudget = when (val x = budget.value.toString()) {
            "" -> 0L
            else -> x.toLong()
        }
//        budgetUseCase.setAmount(savedBudget)
    }
}