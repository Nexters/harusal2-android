package com.nexters.zzallang.harusal2.ui.statement

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

class AddMemoViewModel(private val statementUseCase: StatementUseCase,
                       private val budgetUseCase: BudgetUseCase): BaseViewModel() {
    private val _stateAmount = MutableLiveData("0")
    private val _stateDate = MutableLiveData("")
    val stateMemo = MutableLiveData("")
    val stateAmount: LiveData<String> get() = _stateAmount
    val stateDate: LiveData<String> get() = _stateDate

    private var statementType = Constants.STATEMENT_TYPE_OUT

    fun setAmount(amount: String){
        _stateAmount.postValue(amount+"원")
    }

    fun setType(type: Int){
        statementType = type
    }

    fun setDate(date: String){
        _stateDate.postValue(date)
    }

    fun getDateForNow(): String{
        return SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault()).format(DateUtils.getTodayDate())
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

    fun stringToDate(date: String): Date{
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.set(
            date.substring(0,4).toInt(),
            date.substring(5,7).toInt(),
            date.substring(8).toInt())
        return calendar.time
    }

    suspend fun createStatement(){
        val statementData = Statement(date = stringToDate(stateDate.value?:getDateForNow()), content = stateMemo.value ?: "", amount = (stateAmount.value ?: "0").toInt(), type = statementType)
        statementUseCase.insertData(statementData)
    }
}
