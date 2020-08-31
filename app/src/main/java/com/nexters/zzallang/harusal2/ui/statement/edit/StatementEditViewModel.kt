package com.nexters.zzallang.harusal2.ui.statement.edit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.zzallang.harusal2.application.util.Constants
import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.data.entity.Statement
import com.nexters.zzallang.harusal2.usecase.BudgetUseCase
import com.nexters.zzallang.harusal2.usecase.StatementUseCase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class StatementEditViewModel(private val statementUseCase: StatementUseCase,
                             private val budgetUseCase: BudgetUseCase): BaseViewModel() {
    private val _statementDate = MutableLiveData("")
    val statementAmount = MutableLiveData("0")
    val statementMemo = MutableLiveData("")
    val statementDate: LiveData<String> get() = _statementDate

    private var statementType = Constants.STATEMENT_TYPE_DEFALT
    private lateinit var statement: Statement

    fun setStatement(id: Long){
        // TODO 뷰 연결 후 if-else 제거
        if(id == 0L) statement = Statement(0L, DateUtils.getTodayDate(), "메모", 0, Constants.STATEMENT_TYPE_DEFALT)
        else{
            launch {
                statement = statementUseCase.getData(id)!!
            }
        }
    }

    fun initData(){
        _statementDate.postValue(SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault()).format(statement.date))
        statementAmount.postValue(statement.amount.toString())
        statementMemo.postValue(statement.content)
    }

    fun setType(type: Int){
        statementType = type
    }

    fun setDate(date: String){
        _statementDate.postValue(date)
    }

    fun getDateForNow(): String{
        return SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault()).format(DateUtils.getTodayDate())
    }

    fun stringToDate(inputDate: String): Date{
        val date = Date()
        date.year = inputDate.substring(0,4).toInt()
        date.month = inputDate.substring(5,7).toInt()
        date.date = inputDate.substring(8).toInt()
        return date
    }

    fun getMinDate(): Long{
        val minDate = Calendar.getInstance()
        minDate.time = stringToDate(statementDate.value?:getDateForNow())
        minDate.set(minDate.time.year, minDate.time.month, minDate.time.date-15)
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
        maxDate.time = stringToDate(statementDate.value?:getDateForNow())
        maxDate.set(maxDate.time.year, maxDate.time.month, maxDate.time.date+15)
        launch {
            if(budgetUseCase.findRecentBudget() != null){
                val endDate = budgetUseCase.findRecentBudget()!!.endDate
                maxDate.set(endDate.year, endDate.month, endDate.date)
            }
        }
        return maxDate.time.time
    }

    suspend fun updateStatement(){
        val statementModel = Statement(date = stringToDate(statementDate.value?:getDateForNow()), content = statementMemo.value ?: "", amount = (statementAmount.value ?: "0").toInt(), type = statementType)
        statementUseCase.updateStatement(statementModel)
    }
}