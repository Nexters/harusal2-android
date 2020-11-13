package com.nexters.zzallang.harusal2.usecase

import com.nexters.zzallang.harusal2.data.entity.Statement
import com.nexters.zzallang.harusal2.data.repository.StatementRepository
import java.util.*

class StatementUseCase(private val statementRepository: StatementRepository) {
    suspend fun getData(id: Long) : Statement = statementRepository.getStatement(id)

    suspend fun insertData(statement: Statement) {
        statementRepository.insertStatement(statement)
    }

    suspend fun updateStatement(statement: Statement) {
        statementRepository.updateStatement(statement)
    }

    suspend fun deleteStatement(id: Long) {
        statementRepository.deleteStatement(id)
    }

    suspend fun deleteAllStatement() {
        statementRepository.deleteAllStatement()
    }

    suspend fun findByDate(startDate: Date) : List<Statement> {
        val endDate = startDate.clone() as Date
        startDate.hours = 0
        startDate.minutes = 0
        startDate.seconds = 0
        startDate.time -= 1000

        endDate.hours = 23
        endDate.minutes = 59
        endDate.seconds = 59

        return statementRepository.selectAllStatementByDate(
            startTime = startDate,
            endTime = endDate
        )
    }

    suspend fun findStatementByBudgetId(budgetId: Long): List<Statement> {
        return statementRepository.findStatementByBudgetId(budgetId)
    }
}