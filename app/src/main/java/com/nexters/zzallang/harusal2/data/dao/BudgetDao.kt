package com.nexters.zzallang.harusal2.data.dao

import androidx.room.*
import com.nexters.zzallang.harusal2.data.entity.Budget

@Dao
interface BudgetDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBudget(budget: Budget)

    @Query("select * from budget order by id desc LIMIT 1")
    suspend fun findRecentBudget(): Budget

    @Query("select * from budget where id = :id")
    suspend fun findById(id: Long): Budget

    @Query("select * from budget")
    suspend fun findAll(): List<Budget>

    @Update
    suspend fun update(budget: Budget)
}