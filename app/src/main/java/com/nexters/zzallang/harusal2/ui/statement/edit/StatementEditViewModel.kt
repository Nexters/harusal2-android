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

    fun setType(type: Int){
        statementType = type
    }

    suspend fun updateStatement(){
        val statementModel = Statement(date = Date(System.currentTimeMillis()), content = statementMemo.value ?: "", amount = (statementAmount.value ?: "0").toInt(), type = statementType)
        statementUseCase.updateStatement(statementModel)
    }
}