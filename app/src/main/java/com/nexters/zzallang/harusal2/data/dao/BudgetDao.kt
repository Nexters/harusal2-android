package com.nexters.zzallang.harusal2.data.dao

import androidx.room.*
import com.nexters.zzallang.harusal2.data.entity.Budget

@Dao
interface BudgetDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertBudget(budget: Budget)

    @Query("select * from budget order by id desc LIMIT 1")
    suspend fun findRecentBudget(): Budget

    @Query("select * from budget where id = :id")
    suspend fun findById(id: Long): Budget

    @Query("select * from budget")
    suspend fun findAll(): List<Budget>

    @Update
    suspend fun update(budget: Budget)

    @Query("select * from budget order by id desc")
    suspend fun findAllDesc(): List<Budget>

    @Query("select exists (select 1 from budget where id > 0)")
    suspend fun existsBudget(): Boolean

    @Query("delete from budget")
    suspend fun deleteAllBudget()
}