package com.nexters.zzallang.harusal2.ui.statement.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.zzallang.harusal2.constant.Constants
import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.application.util.DateUtils.stringToDate
import com.nexters.zzallang.harusal2.application.util.NumberUtils
import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.data.entity.Statement
import com.nexters.zzallang.harusal2.usecase.BudgetUseCase
import com.nexters.zzallang.harusal2.usecase.StatementUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.ZoneId

class AddMemoViewModel(private val statementUseCase: StatementUseCase,
                       private val budgetUseCase: BudgetUseCase): BaseViewModel() {
    private val _stateAmount = MutableLiveData("0")
    private val _stateDate = MutableLiveData("")
    val stateMemo = MutableLiveData("")
    val stateAmount: LiveData<String> get() = _stateAmount
    val stateDate: LiveData<String> get() = _stateDate

    private var statementType = Constants.STATEMENT_TYPE_OUT

    fun setAmount(amount: String){
        _stateAmount.postValue(NumberUtils.decimalFormat.format(amount.toInt()) + Constants.MONEY_UNIT)
    }

    private fun getAmount(): Int {
        val amount = _stateAmount.value?.replace(",", "")?: ""
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

    suspend fun createStatement() {
        val memo = if (stateMemo.value?.trim().isNullOrEmpty()) "짤랑짤랑~" else stateMemo.value?.trim() ?: "짤랑짤랑~"
        val statementData = Statement(date = stringToDate(stateDate.value?:getDateForNow()), content = memo, amount = applyType(getAmount()), budgetId = budgetUseCase.findRecentBudget().id)
        statementUseCase.insertData(statementData)
    }
}
