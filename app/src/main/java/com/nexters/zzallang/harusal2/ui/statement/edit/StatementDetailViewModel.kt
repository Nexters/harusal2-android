package com.nexters.zzallang.harusal2.ui.statement.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.zzallang.harusal2.constant.Constants
import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.application.util.NumberUtils
import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.data.entity.Statement
import com.nexters.zzallang.harusal2.usecase.StatementUseCase
import kotlin.math.abs

class StatementDetailViewModel(private val statementUseCase: StatementUseCase) : BaseViewModel() {
    private lateinit var statement: Statement
    private val _statementType = MutableLiveData("")
    private val _statementAmount = MutableLiveData("")
    private val _statementMemo = MutableLiveData("")
    private val _statementDate = MutableLiveData("")
    val statementType: LiveData<String> get() = _statementType
    val statementAmount: LiveData<String> get() = _statementAmount
    val statementMemo: LiveData<String> get() = _statementMemo
    val statementDate: LiveData<String> get() = _statementDate

    suspend fun setStatement(id: Long) {
        statement = statementUseCase.getData(id)
    }

    fun getId(): Long {
        return statement.id
    }

    private fun setType() {
        var type = "나간 돈"
        if (statement.amount >= 0) {
            type = "들어온 돈"
        }
        _statementType.postValue(type)
    }

    private fun setDate() {
        _statementDate.postValue(
            DateUtils.toString(statement.date, Constants.DATE_FORMAT)
        )
    }

    private fun setAmount() {
        _statementAmount.postValue(NumberUtils.decimalFormat.format(abs(statement.amount)) + Constants.MONEY_UNIT)
    }

    private fun setMemo() {
        _statementMemo.postValue(statement.content)
    }

    fun getType(): Int {
        var type = Constants.STATEMENT_TYPE_OUT
        if (statement.amount >= 0) {
            type = Constants.STATEMENT_TYPE_IN
        }
        return type
    }

    fun update() {
        setType()
        setDate()
        setAmount()
        setMemo()
    }

    suspend fun deleteStatement() {
        statementUseCase.deleteStatement(getId())
    }
}