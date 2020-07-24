package com.nexters.zzallang.harusal2.usecase

import com.nexters.zzallang.harusal2.data.entity.Statement
import com.nexters.zzallang.harusal2.data.repository.StatementRepository

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
}