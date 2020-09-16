package com.nexters.zzallang.harusal2.ui.statement.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.zzallang.harusal2.application.util.Constants
import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.data.entity.Statement
import com.nexters.zzallang.harusal2.usecase.BudgetUseCase
import com.nexters.zzallang.harusal2.usecase.StatementUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
        _stateAmount.postValue(amount+Constants.MONEY_UNIT)
    }

    fun getAmount(): Int{
        val amount = _stateAmount.value?:""
        return amount.substring(0, amount.length-1).toInt()
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
        _stateDate.postValue(date)
    }

    fun getDateForNow(): String{
        return SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault()).format(DateUtils.getTodayDate())
    }

    suspend fun getMinDate(): Long{
        val minDate = Calendar.getInstance()
        val budget = withContext(Dispatchers.IO + job){
            budgetUseCase.findRecentBudget()?.let {
                val startDate = it.startDate
                minDate.time = startDate
            }
        }
        return minDate.time.time
    }

    suspend fun getMaxDate(): Long{
        val maxDate = Calendar.getInstance()
        val budget = withContext(Dispatchers.IO + job){
            budgetUseCase.findRecentBudget()?.let {
                val endDate = it.endDate
                maxDate.time = endDate
            }
        }
        return maxDate.time.time
    }

    fun stringToDate(inputDate: String): Date{
        val date = Date()
        date.year = inputDate.substring(0,4).toInt()-1900
        date.month = inputDate.substring(5,7).toInt()-1
        date.date = inputDate.substring(8).toInt()
        return date
    }

    suspend fun createStatement(){
        val statementData = Statement(date = stringToDate(stateDate.value?:getDateForNow()), content = stateMemo.value ?: "", amount = applyType(getAmount()), budgetId = budgetUseCase.findRecentBudget().id)
        statementUseCase.insertData(statementData)
    }
}
