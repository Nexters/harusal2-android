package com.nexters.zzallang.harusal2.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.ui.main.model.MainStatement
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
    private val _todayLivingExpenses = MutableLiveData<String>("오늘의 생활비 0원")
    val todayLivingExpenses: LiveData<String> get() = _todayLivingExpenses

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

    suspend fun getTodaySpendStatementList(): List<MainStatement> {
        val list = withContext(Dispatchers.IO + job) {
            statementUseCase.getStatementHistoryAtDate(Date(System.currentTimeMillis()))
        }
        val result = arrayListOf<MainStatement>()

        for (item in list) {
            result.add(MainStatement(item.amount, item.content))
        }

        return result
    }

    suspend fun refreshTodayLivingExpenses() {
        val budget = withContext(Dispatchers.IO + job) {
            budgetUseCase.findRecentBudget()
        }

        val today = Date(System.currentTimeMillis())
        // 00:00:00 으로 해야 날짜 계산할 때 오차가 생기지 않음
        today.hours = 0
        today.minutes = 0
        today.seconds = 0

        val remainDay =
            if (budget.startDate.month == today.month) {
                when (budget.startDate.month) {
                    1, 3, 5, 7, 8, 10, 12 -> 31
                    2 -> 28
                    else -> 30
                } - today.date + budget.endDate.date
            } else {
                budget.endDate.date - today.date
            }

        _todayLivingExpenses.postValue("오늘의 생활비 ${(budget.budget / remainDay)}원")
    }

    /* TODO: 현재 소비양을 확인해서 배경 색상 변경 */
//    fun checkCurrentSpendState() {
//        if (_todaySpendMoney.value)
//    }
}