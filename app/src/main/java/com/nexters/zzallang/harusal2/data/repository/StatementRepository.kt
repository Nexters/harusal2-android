package com.nexters.zzallang.harusal2.data.repository

import com.nexters.zzallang.harusal2.application.AppDatabase
import com.nexters.zzallang.harusal2.base.BaseRepository
import com.nexters.zzallang.harusal2.data.entity.Statement
import kotlinx.coroutines.withContext
import java.util.*

class StatementRepository : BaseRepository() {
    suspend fun insertStatement(statement: Statement) {
        withContext(coroutineContext) {
            AppDatabase.instance.statementDao().insertStatement(statement)
        }
    }

    suspend fun getStatement(id: Long) =
        withContext(coroutineContext) {
            AppDatabase.instance.statementDao().getStatement(id)
        }

    suspend fun updateStatement(statement: Statement) =
        withContext(coroutineContext) {
            AppDatabase.instance.statementDao().updateStatement(statement)
        }

    suspend fun deleteStatement(id: Long) {
        withContext(coroutineContext) {
            AppDatabase.instance.statementDao().deleteStatement(id)
        }
    }

    suspend fun selectAllStatementByDate(startTime: Date, endTime: Date) =
        withContext(coroutineContext) {
            AppDatabase.instance.statementDao().selectStatementWhereDate(startTime, endTime)
        }

    suspend fun findStatementByBudgetId(budgetId: Long) =
        withContext(coroutineContext){
            AppDatabase.instance.statementDao().findStatementByBudgetId(budgetId)
        }
}