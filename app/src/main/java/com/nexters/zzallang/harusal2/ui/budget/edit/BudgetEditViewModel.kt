package com.nexters.zzallang.harusal2.ui.budget.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.zzallang.harusal2.application.util.MoneyUtils
import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.data.entity.Budget
import com.nexters.zzallang.harusal2.usecase.BudgetUseCase
import kotlinx.coroutines.launch

class BudgetEditViewModel(private val budgetUseCase: BudgetUseCase) : BaseViewModel() {
    companion object {
        private const val PREFIX = "일일 생활비 "
    }

    val budget = MutableLiveData("0")
    private var currentBudget: Budget? = null
    private val _hangeulBudget = MutableLiveData("0원")
    private val _averageBudget = MutableLiveData(PREFIX + "0원")
    val hangeulBudget: LiveData<String> get() = _hangeulBudget
    val averageBudget: LiveData<String> get() = _averageBudget

    fun init() {
        launch {
            currentBudget = budgetUseCase.findRecentBudget()
            budget.postValue(currentBudget?.budget.toString())
        }
    }

    fun budgetChanged(text: String) {
        if (text == "") {
            textClear()
            return
        }

        val inputBudget: Long = text.toLong()

        setAverageBudget("${inputBudget/30L} 원")
        setHangeulBudget(inputBudget)
    }

    suspend fun saveBudget(): Boolean {
        try {
            currentBudget?.budget = budget.value?.toInt()!!
            budgetUseCase.updateBudget(currentBudget!!)
            return true
        } catch (e: Exception) {

        }
        return false
    }

    private fun textClear() {
        setHangeulBudget(0)
        setAverageBudget("0원")
    }

    private fun setAverageBudget(averageBudget: String) {
        _averageBudget.postValue(PREFIX + averageBudget)
    }

    private fun setHangeulBudget(budget: Long) {
        _hangeulBudget.postValue(MoneyUtils.convertString(budget))
    }
}