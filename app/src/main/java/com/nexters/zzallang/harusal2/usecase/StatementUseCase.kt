package com.nexters.zzallang.harusal2.usecase

import com.nexters.zzallang.harusal2.data.entity.Statement
import com.nexters.zzallang.harusal2.data.repository.StatementRepository

class StatementUseCase(private val repo: StatementRepository) {
    suspend fun getData(id: Long) = repo.getStatement(id)

    suspend fun insertData(statement: Statement) {
        repo.insertStatement(statement)
    }

    suspend fun updateStatement(statement: Statement) {
        repo.updateStatement(statement)
    }
}