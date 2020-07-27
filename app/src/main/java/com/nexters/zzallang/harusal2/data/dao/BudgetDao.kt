package com.nexters.zzallang.harusal2.data.dao

import androidx.room.*
import com.nexters.zzallang.harusal2.data.entity.Budget

@Dao
interface BudgetDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBudget(budget: Budget)

    @Query("SELECT * FROM budget ORDER BY budget_id")
    suspend fun getAllBudgets(): List<Budget>

    @Query("SELECT * FROM budget WHERE budget_id = :budgetId")
    suspend fun getBudgetWithId(budgetId: Long): Budget

    @Update
    suspend fun updateBudget(budget: Budget)

    @Query("UPDATE budget SET amount = :amount WHERE budget_id = :budgetId")
    suspend fun updateBudgetAmountWithId(budgetId: Long, amount: Long)

    @Query("UPDATE budget SET amount = :startDate WHERE budget_id = :budgetId")
    suspend fun updateBudgetDateWithId(budgetId: Long, startDate: Int)

    @Query("DELETE FROM budget WHERE budget_id = :budgetId")
    suspend fun deleteBudgetWithId(budgetId: Long)
}