package com.nexters.zzallang.harusal2.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.nexters.zzallang.harusal2.data.DateConverter
import java.time.LocalDate

@Entity(tableName = "budget")
@TypeConverters(DateConverter::class)
data class Budget(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0L,
    @ColumnInfo(name = "budget") var budget: Int,
    @ColumnInfo(name = "startDate") val startDate: LocalDate,
    @ColumnInfo(name = "endDate") var endDate: LocalDate
)