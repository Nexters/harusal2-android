package com.nexters.zzallang.harusal2.ui.statement.edit

import com.nexters.zzallang.harusal2.application.util.Constants
import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.data.entity.Statement
import com.nexters.zzallang.harusal2.usecase.StatementUseCase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class StatementDetailViewModel(private val statementUseCase: StatementUseCase): BaseViewModel() {
    private lateinit var statement: Statement

    fun setStatement(id: Long){
        launch {
            statement = statementUseCase.getData(id)!!
        }
    }

    fun getId(): Long{
        return statement.id
    }

    fun getDate(): String{
        return SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault()).format(statement.date)
    }

    fun getAmount(): String{
        return statement.amount.toString() + "원"
    }

    fun getMemo(): String{
        return statement.content
    }
}