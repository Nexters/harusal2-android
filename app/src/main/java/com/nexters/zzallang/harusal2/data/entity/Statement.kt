package com.nexters.zzallang.harusal2.data.entity

import androidx.room.*
import com.nexters.zzallang.harusal2.data.DateConverter
import java.util.*

@Entity(tableName = "statement")
@TypeConverters(DateConverter::class)
data class Statement(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "budget_id") val budgetId: Long,
    @ColumnInfo(name = "date") val date: Date,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "amount") val amount: Int,
    @ColumnInfo(name = "type") val type: Int
)