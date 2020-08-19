package com.nexters.zzallang.harusal2.ui.statement.edit

import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.usecase.StatementUseCase

class StatementViewModel(private val statementUseCase: StatementUseCase): BaseViewModel() {
    suspend fun deleteStatement(id: Long){
        statementUseCase.deleteStatement(id)
    }
}