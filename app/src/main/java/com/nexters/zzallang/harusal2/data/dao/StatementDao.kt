package com.nexters.zzallang.harusal2.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nexters.zzallang.harusal2.data.entity.Statement

@Dao
interface StatementDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertStatement(statement: Statement)

    @Query("select * from statement where id = :id")
    suspend fun getStatement(id: Long): Statement
}