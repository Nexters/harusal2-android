package com.nexters.zzallang.harusal2.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.zzallang.harusal2.application.util.DateUtils
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
            if (item.content.length > 10) {
                item.content = item.content.substring(0..10).plus("...")
            }
            result.add(MainStatement(item.id, item.amount, item.content))
        }

        return result
    }

    private suspend fun refreshTodayLivingExpenses() {
        budget = withContext(Dispatchers.IO + job) {
            budgetUseCase.findRecentBudget()
        }

        val remainDate = DateUtils.calculateDate(Date(), budget.endDate)

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