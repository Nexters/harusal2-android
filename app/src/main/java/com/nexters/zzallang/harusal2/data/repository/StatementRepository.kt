package com.nexters.zzallang.harusal2.data.repository

import com.nexters.zzallang.harusal2.application.AppDatabase
import com.nexters.zzallang.harusal2.data.entity.Statement
import java.time.LocalDate

class StatementRepository {
    suspend fun insertStatement(statement: Statement) {
        AppDatabase.instance.statementDao().insertStatement(statement)
    }

    suspend fun getStatement(id: Long): Statement {
        return AppDatabase.instance.statementDao().getStatement(id)
    }

    suspend fun updateStatement(statement: Statement) {
        AppDatabase.instance.statementDao().updateStatement(statement)
    }

    suspend fun deleteStatement(id: Long) {
        AppDatabase.instance.statementDao().deleteStatement(id)
    }

    suspend fun deleteAllStatement() {
        AppDatabase.instance.statementDao().deleteAllStatement()
    }

    suspend fun selectAllStatementByDate(
        startTime: LocalDate,
        endTime: LocalDate,
    ): List<Statement> {
        return AppDatabase.instance.statementDao().selectStatementWhereDate(startTime, endTime)
    }

    suspend fun findStatementByBudgetId(budgetId: Long): List<Statement> {
        return AppDatabase.instance.statementDao().findStatementByBudgetId(budgetId)
    }
}
