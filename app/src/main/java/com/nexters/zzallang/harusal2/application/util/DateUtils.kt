package com.nexters.zzallang.harusal2.application.util

import java.util.*

object DateUtils {
    fun getLastDayOfMonth(): Int {
        return getLastDayOfMonthAfter(0)
    }

    fun getLastDayOfMonthAfter(month: Int): Int {
        val date = Date()
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.MONTH, month)
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    fun getDay(): Int {
        val date = Date()
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.DAY_OF_MONTH)
    }

    fun getMonth(): Int {
        val date = Date()
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.MONTH)
    }
}