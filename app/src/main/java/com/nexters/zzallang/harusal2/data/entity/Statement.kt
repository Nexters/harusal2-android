package com.nexters.zzallang.harusal2.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.nexters.zzallang.harusal2.data.DateConverter
import java.util.*

@Entity(tableName = "statement")
@TypeConverters(DateConverter::class)
data class Statement(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0L,
    @ColumnInfo(name = "date") val date: Date,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "amount") val amount: Int
)