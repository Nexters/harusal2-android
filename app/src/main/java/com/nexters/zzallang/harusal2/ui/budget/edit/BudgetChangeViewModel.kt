package com.nexters.zzallang.harusal2.ui.budget.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.zzallang.harusal2.application.util.MoneyUtils
import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.usecase.BudgetUseCase

class BudgetChangeViewModel(private val budgetUseCase: BudgetUseCase) : BaseViewModel() {
    companion object {
        private const val PREFIX = "일일 생활비 "
    }

    val budget = MutableLiveData("")

    private val _hangeulBudget = MutableLiveData("0원")
    private val _averageBudget = MutableLiveData(PREFIX + "0원")
    val hangeulBudget: LiveData<String> get() = _hangeulBudget
    val averageBudget: LiveData<String> get() = _averageBudget

    init {
        val initBudget = budgetUseCase.getAmount()
        budget.postValue(initBudget.toString())
    }

    private fun textClear() {
        setHangleBudget(0)
        setAverageBudget("0원")
        return
    }

    fun budgetChanged(text: String) {
        if (text == "") {
            textClear()
            return
        }

        val inputBudget: Long = java.lang.Long.valueOf(text)

        setAverageBudget((java.lang.Long.valueOf(text) / 30L).toString() + "원")
        setHangleBudget(inputBudget)
    }

    private fun setAverageBudget(averageBudget: String) {
        _averageBudget.postValue(PREFIX + averageBudget)
    }

    private fun setHangleBudget(budget: Long) {
        _hangeulBudget.postValue(MoneyUtils.convertString(budget))
    }

    fun saveBudget() {
        val savedBudget = when (val x = budget.value.toString()) {
            "" -> 0L
            else -> x.toLong()
        }
        budgetUseCase.setAmount(savedBudget)
    }
}