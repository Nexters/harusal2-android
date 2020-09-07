package com.nexters.zzallang.harusal2.usecase

import com.nexters.zzallang.harusal2.data.entity.Statement
import com.nexters.zzallang.harusal2.data.repository.StatementRepository
import java.util.*

class StatementUseCase(private val statementRepository: StatementRepository) {
    suspend fun getData(id: Long) : Statement? = statementRepository.getStatement(id)

    suspend fun insertData(statement: Statement) {
        statementRepository.insertStatement(statement)
    }

    suspend fun updateStatement(statement: Statement) {
        statementRepository.updateStatement(statement)
    }

    suspend fun deleteStatement(id: Long) {
        statementRepository.deleteStatement(id)
    }

    suspend fun getStatementHistoryAtMonth(date: Date) : List<Statement> {
        date.date = 1
        date.hours = 0
        date.minutes = 0
        date.seconds = 0
        val startTime = date.time

        date.date = when(date.month) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            2 -> if (date.year % 4 == 0) {
                29
            } else {
                28
            }
            else -> 30
        }

        date.hours = 23
        date.minutes = 59
        date.seconds = 59
        val endTime = date.time

        return statementRepository.selectAllStatementByDate(startTime, endTime)
    }

    suspend fun findByStartDateBetweenEndDate(startDate: Date, endDate: Date) : List<Statement> {
        return statementRepository.selectAllStatementByDate(
            startTime = startDate.time,
            endTime = endDate.time
        )
    }

    suspend fun getStatementHistoryAtDate(date: Date) : List<Statement> {
        date.hours = 0
        date.minutes = 0
        date.seconds = 0
        val startTime = date.time

        date.hours = 23
        date.minutes = 59
        date.seconds = 59
        val endTime = date.time

        return statementRepository.selectAllStatementByDate(startTime, endTime)
    }

    suspend fun findByDate(startDate: Date) : List<Statement> {
        val endDate = startDate.clone() as Date
        startDate.hours = 0
        startDate.minutes = 0
        startDate.seconds = 0

        endDate.hours = 23
        endDate.minutes = 59
        endDate.seconds = 59

        return statementRepository.selectAllStatementByDate(
            startTime = startDate.time,
            endTime = endDate.time
        )
    }
}