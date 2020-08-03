package com.nexters.zzallang.harusal2.ui.register

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.nexters.zzallang.harusal2.application.util.MoneyUtils
import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.usecase.BudgetUseCase


class BudgetRegisterViewModel(private val budgetUseCase: BudgetUseCase) : BaseViewModel() {
    val budget = MutableLiveData("")
    val hangeulBudget = MutableLiveData("")
    val averageBudget = MutableLiveData("")

    fun budgetChanged(text: String) {
        if (text == "") {
            hangeulBudget.postValue("0원")
            averageBudget.postValue("0원")
            averageBudget.postValue("")
            return
        }

        val inputBudget: Long = java.lang.Long.valueOf(text)
        budget.postValue(text)

        averageBudget.postValue((inputBudget / 30L).toString() + "원")
        hangeulBudget(inputBudget)
    }

    fun hangeulBudget(budget: Long) {
        this.hangeulBudget.postValue(MoneyUtils.convertString(budget))
    }

    fun toNext(activity: Activity){
        val savedBudget = when(val x = budget.value.toString()){
            "" -> 0L
            else -> x.toLong()
        }

        budgetUseCase.setAmount(savedBudget)

        val intent = Intent(activity, BudgetDayRegisterActivity::class.java)
        activity.startActivity(intent)
    }
}