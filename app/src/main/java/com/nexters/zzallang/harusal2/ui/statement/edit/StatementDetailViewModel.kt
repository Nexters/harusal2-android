package com.nexters.zzallang.harusal2.ui.statement.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.zzallang.harusal2.application.util.Constants
import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.data.entity.Statement
import com.nexters.zzallang.harusal2.usecase.StatementUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class StatementDetailViewModel(private val statementUseCase: StatementUseCase): BaseViewModel() {
    private lateinit var statement: Statement
    private val _statementType = MutableLiveData("")
    val statementType: LiveData<String> get() = _statementType

    suspend fun setStatement(id: Long){
        statement = withContext(Dispatchers.IO + job){
            statementUseCase.getData(id)
        }
    }

    fun setType(): Int{
        return if(statement.amount < 0){
            _statementType.postValue("나간 돈")
            Constants.STATEMENT_TYPE_OUT
        } else{
            _statementType.postValue("들어온 돈")
            Constants.STATEMENT_TYPE_IN
        }
    }

    fun getId(): Long{
        return statement.id
    }

    fun getDate(): String{
        return SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault()).format(statement.date)
    }

    fun getAmount(): String{
        return statement.amount.toString() + Constants.MONEY_UNIT
    }

    fun getMemo(): String{
        return statement.content
    }
}