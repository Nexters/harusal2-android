package com.nexters.zzallang.harusal2.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.data.entity.Statement
import com.nexters.zzallang.harusal2.usecase.BudgetUseCase
import com.nexters.zzallang.harusal2.usecase.StatementUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainViewModel(
    private val statementUseCase: StatementUseCase,
    private val budgetUseCase: BudgetUseCase
) : BaseViewModel() {
    private val _todaySpendMoney = MutableLiveData<String>("0원")
    val todaySpendMoney: LiveData<String> get() = _todaySpendMoney

    fun refreshTodaySpendMoney() {
        launch {
            val list = statementUseCase.getStatementHistoryAtDate(Date(System.currentTimeMillis()))

            var tempMoney = 0
            for (item in list) {
                tempMoney += item.amount
            }

            _todaySpendMoney.postValue("${tempMoney}원")
        }
    }
}