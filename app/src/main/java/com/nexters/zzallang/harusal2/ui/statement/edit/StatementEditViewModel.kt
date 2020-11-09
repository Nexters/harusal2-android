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
import java.text.SimpleDateFormat
import java.util.*
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
        _statementDate.postValue(SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault()).format(statement.date))
        statementAmount.postValue(abs(statement.amount).toString())
        statementMemo.postValue(statement.content)
    }

    fun initType(): Int{
        if(statement.amount.toString()[0] == '-') return Constants.STATEMENT_TYPE_OUT
        return Constants.STATEMENT_TYPE_IN
    }

    fun setType(type: Int){
        statementType = type
    }


    fun applyType(amount: Int): Int{
        var resultAmount = amount;
        if(statementType == Constants.STATEMENT_TYPE_OUT) resultAmount*=-1
        return resultAmount
    }

    fun setDate(date: String){
        _statementDate.postValue(date)
    }

    fun getDateForNow(): String{
        return SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault()).format(DateUtils.getTodayDate())
    }

    fun getInitDate(): Date{
        return stringToDate(SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault()).format(statement.date))
    }

    suspend fun getMinDate(): Long{
        val minDate = Calendar.getInstance()
        withContext(Dispatchers.IO + job){
            budgetUseCase.findRecentBudget()?.let {
                val startDate = it.startDate
                minDate.time = startDate
            }
        }
        return minDate.time.time
    }

    suspend fun getMaxDate(): Long{
        val maxDate = Calendar.getInstance()
        withContext(Dispatchers.IO + job){
            budgetUseCase.findRecentBudget()?.let {
                val endDate = it.endDate
                maxDate.time = endDate
            }
        }
        return maxDate.time.time
    }

    suspend fun updateStatement(){
        val memo = if (statementMemo.value?.trim().isNullOrEmpty()) "짤랑짤랑~" else statementMemo.value?.trim() ?: "짤랑짤랑~"
        val statementModel = Statement(id = statement.id, date = stringToDate(statementDate.value?:getDateForNow()), content = memo, amount = applyType((statementAmount.value ?: "0").toInt()), budgetId = budgetUseCase.findRecentBudget().id)
        statementUseCase.updateStatement(statementModel)
    }

    suspend fun deleteStatement(){
        statementUseCase.deleteStatement(getId())
    }
}