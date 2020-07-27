package com.nexters.zzallang.harusal2.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budget")
data class Budget (
    @PrimaryKey @ColumnInfo(name = "budget_id") val budgetId: Long,
    @ColumnInfo(name = "amount") val amount: Long?,
    @ColumnInfo(name = "start_date") val startDate: Int?
)