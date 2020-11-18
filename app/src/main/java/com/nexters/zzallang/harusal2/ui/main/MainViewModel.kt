package com.nexters.zzallang.harusal2.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.data.entity.Budget
import com.nexters.zzallang.harusal2.data.entity.Statement
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
    private lateinit var budget: Budget
    private lateinit var statements: List<Statement>
    private lateinit var todayStatements: List<Statement>
    private var remainMoney: Int = 0
    private var livingExpenses: Int = 0

    private fun refreshTodaySpendMoney() {
        var tempMoney = livingExpenses
        for (item in todayStatements) {
            tempMoney += item.amount
        }

        remainMoney = tempMoney
        _todayRemainMoney.postValue("${remainMoney}원")
    }

    fun getTodaySpendStatementList(): List<MainStatement> {
        val result = arrayListOf<MainStatement>()

        for (item in todayStatements) {
            if (item.content.length > 10) {
                item.content = item.content.substring(0..10).plus("...")
            }
            result.add(MainStatement(item.id, item.amount, item.content))
        }

        return result
    }

    private fun refreshTodayLivingExpenses() {
        val yesterdayMillis = 1000 * 60 * 60 * 24
        var spentMoney = 0
        var yesterdayDate = Date(Date().time - yesterdayMillis)
        yesterdayDate.hours = 23
        yesterdayDate.minutes = 59
        yesterdayDate.seconds = 59

        statements.filter { statement -> statement.date.before(yesterdayDate) }
            .forEach { statement: Statement -> spentMoney += statement.amount }

        val remainDate = DateUtils.calculateDate(Date(), budget.endDate)

        livingExpenses = (budget.budget + spentMoney) / remainDate
        _todayLivingExpenses.postValue("오늘의 생활비 ${livingExpenses}원")
    }

    suspend fun checkCurrentSpendState(): SpendState {
        budget = withContext(Dispatchers.IO + job) {
            budgetUseCase.findRecentBudget()
        }

        statements = withContext(Dispatchers.IO + job) {
            statementUseCase.findStatementByBudgetId(budget.id)
        }

        todayStatements = statements.filter { statement -> statement.date.date == Date().date }

        refreshTodayLivingExpenses()
        refreshTodaySpendMoney()
        val todayStatementSize = getTodaySpendStatementList().size

        return if (todayStatementSize == 0) {
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