package com.nexters.zzallang.harusal2.ui.statement.edit

import androidx.lifecycle.MutableLiveData
import com.nexters.zzallang.harusal2.application.util.Constants
import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.data.entity.Statement
import com.nexters.zzallang.harusal2.usecase.StatementUseCase
import java.util.*

class StatementEditViewModel(private val statementUseCase: StatementUseCase): BaseViewModel() {
    val statementAmount = MutableLiveData("0")
    val statementMemo = MutableLiveData("")
    private var statementType = Constants.STATEMENT_TYPE_DEFALT

    fun setTypeIn(){
        statementType = Constants.STATEMENT_TYPE_IN
    }

    fun setTypeOut(){
        statementType = Constants.STATEMENT_TYPE_OUT
    }

    /*
     TODO :
     id > auto로 해야하지 않는가 제안하기
     Date format 물어보기
     amount and memo nullable 고민
     */
    suspend fun updateStatement(){
        val statementModel = Statement(0L, Date(), statementMemo.value ?: "", (statementAmount.value ?: "0").toInt(), statementType)
        statementUseCase.updateStatement(statementModel)
    }
}