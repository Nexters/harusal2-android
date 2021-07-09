package com.nexters.zzallang.harusal2.data

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class DateConverter {
    @TypeConverter
    fun toDate(date: Long): LocalDate =
        Instant.ofEpochMilli(date).atZone(ZoneId.systemDefault()).toLocalDate()

    @TypeConverter
    fun toLong(localDate: LocalDate) = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}