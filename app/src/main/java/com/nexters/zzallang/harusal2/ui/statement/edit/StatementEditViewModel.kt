package com.nexters.zzallang.harusal2.ui.statement.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.zzallang.harusal2.application.util.Constants
import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.application.util.DateUtils.stringToDate
import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.data.entity.Statement
import com.nexters.zzallang.harusal2.usecase.BudgetUseCase
import com.nexters.zzallang.harusal2.usecase.StatementUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.ZoneId
import kotlin.math.abs

class StatementEditViewModel(private val statementUseCase: StatementUseCase,
                             private val budgetUseCase: BudgetUseCase): BaseViewModel() {
    private val _statementDate = MutableLiveData("")
    val statementAmount = MutableLiveData("")
    val statementMemo = MutableLiveData("")
    val statementDate: LiveData<String> get() = _statementDate

    private var statementType = Constants.STATEMENT_TYPE_DEFALT
    private lateinit var statement: Statement

    suspend fun setStatement(id: Long){
        statement = withContext(Dispatchers.IO + job){
            statementUseCase.getData(id)
        }
    }

    fun getId():Long{
        return statement.id
    }

    fun initData(){
        _statementDate.postValue(DateUtils.toString(statement.date, Constants.DATE_FORMAT))
        statementAmount.postValue(abs(statement.amount).toString())
        statementMemo.postValue(statement.content)
    }

    fun initType(): Int{
        var type = Constants.STATEMENT_TYPE_DEFALT
        if(statement.amount.toString()[0] != '-') {
            type = Constants.STATEMENT_TYPE_IN
        }
        setType(type)
        return type
    }

    fun setType(type: Int){
        statementType = type
    }


    fun applyType(amount: Int): Int{
        var resultAmount = amount
        if(statementType == Constants.STATEMENT_TYPE_OUT) resultAmount*=-1
        return resultAmount
    }

    fun setDate(date: String){
        _statementDate.postValue(date)
    }

    fun getDateForNow(): String{
        return DateUtils.toString(LocalDate.now(), Constants.DATE_FORMAT)
    }

    suspend fun getMinDate(): Long{
        var minDate = LocalDate.now()
        withContext(Dispatchers.IO + job){
            budgetUseCase.findRecentBudget()?.let {
                minDate = it.startDate
            }
        }
        return minDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    suspend fun getMaxDate(): Long{
        var maxDate = LocalDate.now()
        withContext(Dispatchers.IO + job){
            budgetUseCase.findRecentBudget()?.let {
                maxDate = it.endDate
            }
        }
        return maxDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    suspend fun updateStatement(){
        val memo = if (statementMemo.value?.trim().isNullOrEmpty()) "짤랑짤랑~" else statementMemo.value?.trim() ?: "짤랑짤랑~"
        val statementModel = Statement(
            id = statement.id,
            date = stringToDate(statementDate.value?:getDateForNow()),
            content = memo,
            amount = applyType((statementAmount.value ?: "0").toInt()),
            budgetId = budgetUseCase.findRecentBudget().id)
        statementUseCase.updateStatement(statementModel)
    }

    suspend fun deleteStatement(){
        statementUseCase.deleteStatement(getId())
    }
}