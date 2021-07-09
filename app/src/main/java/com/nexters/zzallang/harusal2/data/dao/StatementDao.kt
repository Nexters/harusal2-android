package com.nexters.zzallang.harusal2.data.dao

import androidx.room.*
import com.nexters.zzallang.harusal2.data.entity.Statement
import java.time.LocalDate

@Dao
interface StatementDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStatement(statement: Statement)

    @Query("select * from statement where id = :id")
    suspend fun getStatement(id: Long): Statement

    @Update
    suspend fun updateStatement(value: Statement)

    @Query("DELETE FROM statement WHERE id= :id")
    suspend fun deleteStatement(id: Long)

    @Query("DELETE FROM statement")
    suspend fun deleteAllStatement()

    @Query("select * from statement where date between :startTime AND :endTime ORDER BY date, id DESC")
    suspend fun selectStatementWhereDate(startTime: LocalDate, endTime: LocalDate): List<Statement>

    @Query("select * from statement where budget_id = :budgetId ORDER BY date DESC, id DESC")
    suspend fun findStatementByBudgetId(budgetId: Long): List<Statement>
}