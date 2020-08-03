package com.nexters.zzallang.harusal2.ui.register

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.nexters.zzallang.harusal2.application.util.MoneyUtils
import com.nexters.zzallang.harusal2.base.BaseViewModel


class BudgetRegisterViewModel : BaseViewModel() {
    val budget: MutableLiveData<String> = MutableLiveData("")
    val hangeulBudget: MutableLiveData<String> = MutableLiveData("")
    val averageBudget: MutableLiveData<String> = MutableLiveData("")

    fun budgetChanged(text: String) {
        if (text == "") {
            hangeulBudget.postValue("0원")
            averageBudget.postValue("0원")
            averageBudget.postValue("")
            return
        }

        var inputBudget: Long = java.lang.Long.valueOf(text)
        budget.postValue(text)

        averageBudget.postValue((inputBudget / 30L).toString() + "원")
        hangeulBudget(inputBudget)
    }

    fun hangeulBudget(budget: Long) {
        this.hangeulBudget.postValue(MoneyUtils.convertString(budget) + "원")
    }

    fun toNext(activity: Activity){
        val intent = Intent(activity, BudgetDayRegisterActivity::class.java)
        activity.startActivity(intent)
    }
}