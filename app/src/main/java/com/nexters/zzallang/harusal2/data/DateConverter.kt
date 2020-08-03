package com.nexters.zzallang.harusal2.data

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun toDate(date: Long): Date = Date(date)

    @TypeConverter
    fun toLong(date: Date): Long = date.time
}