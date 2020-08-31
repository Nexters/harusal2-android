package com.nexters.zzallang.harusal2.ui.statement.edit

import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.usecase.StatementUseCase

class StatementViewModel(private val statementUseCase: StatementUseCase): BaseViewModel() {
    private var statementId = 0L

    fun setId(id: Long){
        statementId = id
    }

    fun getId(): Long{
        return statementId
    }

    suspend fun deleteStatement(){
        statementUseCase.deleteStatement(statementId)
    }
}