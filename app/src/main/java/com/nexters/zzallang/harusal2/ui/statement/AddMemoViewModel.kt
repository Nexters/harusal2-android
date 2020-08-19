package com.nexters.zzallang.harusal2.ui.statement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.zzallang.harusal2.application.util.Constants
import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.data.entity.Statement
import com.nexters.zzallang.harusal2.usecase.StatementUseCase
import java.util.*

class AddMemoViewModel(private val statementUseCase: StatementUseCase): BaseViewModel() {
    private val _stateAmount = MutableLiveData("")
    val stateMemo = MutableLiveData("")
    val stateAmount: LiveData<String> get() = _stateAmount

    private var statementType = Constants.STATEMENT_TYPE_OUT

    fun setAmount(amount: String){
        _stateAmount.postValue(amount+"원")
    }

    fun setType(type: Int){
        statementType = type
    }

    // TODO : id auto increment!/ Date format? / memo amount nullable?
    suspend fun createStatement(){
        val statementData = Statement(0L, Date(), stateMemo.value ?: "", (stateAmount.value ?: "0").toInt(), statementType)
        statementUseCase.insertData(statementData)
    }
}