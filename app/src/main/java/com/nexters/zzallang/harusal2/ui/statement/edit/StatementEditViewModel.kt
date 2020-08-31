package com.nexters.zzallang.harusal2.ui.statement.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.zzallang.harusal2.application.util.Constants
import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.data.entity.Statement
import com.nexters.zzallang.harusal2.usecase.BudgetUseCase
import com.nexters.zzallang.harusal2.usecase.StatementUseCase
import kotlinx.coroutines.launch
import java.util.*

class StatementEditViewModel(private val statementUseCase: StatementUseCase,
                             private val budgetUseCase: BudgetUseCase): BaseViewModel() {
    private val _statementDate = MutableLiveData("")
    val statementAmount = MutableLiveData("0")
    val statementMemo = MutableLiveData("")
    val statementDate: LiveData<String> get() = _statementDate

    private var statementType = Constants.STATEMENT_TYPE_DEFALT

    fun setType(type: Int){
        statementType = type
    }

    fun setDate(date: String){
        _statementDate.postValue(date)
    }

    fun getMinDate(): Long{
        val minDate = Calendar.getInstance()
        minDate.set(DateUtils.getYear(), DateUtils.getMonth(), DateUtils.getDay()-15)
        launch {
            if(budgetUseCase.findRecentBudget() != null){
                val startDate = budgetUseCase.findRecentBudget()!!.startDate
                minDate.set(startDate.year, startDate.month, startDate.day)
            }
        }
        return minDate.time.time
    }

    fun getMaxDate(): Long{
        val maxDate = Calendar.getInstance()
        maxDate.set(DateUtils.getYear(), DateUtils.getMonth(), DateUtils.getDay()+15)
        launch {
            if(budgetUseCase.findRecentBudget() != null){
                val endDate = budgetUseCase.findRecentBudget()!!.endDate
                maxDate.set(endDate.year, endDate.month, endDate.date)
            }
        }
        return maxDate.time.time
    }

    suspend fun updateStatement(){
        val statementModel = Statement(date = Date(System.currentTimeMillis()), content = statementMemo.value ?: "", amount = (statementAmount.value ?: "0").toInt(), type = statementType)
        statementUseCase.updateStatement(statementModel)
    }
}