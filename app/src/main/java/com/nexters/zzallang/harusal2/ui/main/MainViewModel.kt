package com.nexters.zzallang.harusal2.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.data.entity.Budget
import com.nexters.zzallang.harusal2.ui.main.model.MainStatement
import com.nexters.zzallang.harusal2.usecase.BudgetUseCase
import com.nexters.zzallang.harusal2.usecase.StatementUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class MainViewModel(
    private val statementUseCase: StatementUseCase,
    private val budgetUseCase: BudgetUseCase
) : BaseViewModel() {
    private val _todayRemainMoney = MutableLiveData<String>("0원")
    val todayRemainMoney: LiveData<String> get() = _todayRemainMoney
    private val _todayLivingExpenses = MutableLiveData<String>("오늘의 생활비 0원")
    val todayLivingExpenses: LiveData<String> get() = _todayLivingExpenses
    lateinit var budget :Budget
    private var remainMoney: Int = 0
    private var livingExpenses: Int = 0

    private suspend fun refreshTodaySpendMoney() {
        val list = withContext(Dispatchers.IO + job) {
            statementUseCase.findStatementByBudgetId(budget.id)
        }.filter { statement -> statement.date.date == Date().date }

        var tempMoney = livingExpenses
        for (item in list) {
            tempMoney += item.amount
        }

        remainMoney = tempMoney
        _todayRemainMoney.postValue("${remainMoney}원")
    }

    suspend fun getTodaySpendStatementList(): List<MainStatement> {
        val list = withContext(Dispatchers.IO + job) {
            statementUseCase.findStatementByBudgetId(budget.id)
        }.filter { statement -> statement.date.date == Date().date }
        val result = arrayListOf<MainStatement>()

        for (item in list) {
            result.add(MainStatement(item.id, item.amount, item.content))
        }

        return result
    }

    private suspend fun refreshTodayLivingExpenses() {
        budget = withContext(Dispatchers.IO + job) {
            budgetUseCase.findRecentBudget()
        }

        val today = Date(System.currentTimeMillis())
        // 00:00:00 으로 해야 날짜 계산할 때 오차가 생기지 않음
        today.hours = 0
        today.minutes = 0
        today.seconds = 0

        val remainDate =
            if (budget.startDate.month == today.month) {
                if (budget.startDate.month == budget.endDate.month) {
                    budget.endDate.date - today.date + 1
                } else {
                    when (budget.startDate.month) {
                        1, 3, 5, 7, 8, 10, 12 -> 31
                        2 -> 28
                        else -> 30
                    } - today.date + budget.endDate.date + 1
                }
            } else {
                budget.endDate.date - today.date + 1
            }

        livingExpenses = budget.budget / remainDate
        _todayLivingExpenses.postValue("오늘의 생활비 ${livingExpenses}원")
    }

    suspend fun checkCurrentSpendState(): SpendState {
        var statementListSize: Int = 0

        withContext(Dispatchers.IO + job) {
            refreshTodayLivingExpenses()
            refreshTodaySpendMoney()
            statementListSize = getTodaySpendStatementList().size
        }

        return if (statementListSize == 0) {
            SpendState.DEFAULT
        } else if ((livingExpenses * (7f / 10)) < remainMoney) {
            SpendState.FLEX
        } else if ((livingExpenses * (1f / 10)) < remainMoney) {
            SpendState.CLAP
        } else if (-(livingExpenses * (4f / 10)) < remainMoney) {
            SpendState.EMBARRASSED
        } else if (-(livingExpenses * (8f / 10)) < remainMoney) {
            SpendState.CRY
        } else {
            SpendState.VOLCANO
        }
    }
}