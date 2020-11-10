package com.nexters.zzallang.harusal2.application.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    private var dateFormatContainYear: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd")
    private var dateFormatNoYear: SimpleDateFormat = SimpleDateFormat("MM.dd")
    private const val MILLIS = 24 * 60 * 60 * 1000

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
        return calendar.get(Calendar.MONTH) + 1
    }

    fun getYear(): Int {
        val date = Date()
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.YEAR)
    }

    fun getToday(): String = "${getYear()}.${getMonth()}.${getDay()}"

    fun getTodayDate(): Date {
        return Date(System.currentTimeMillis())
    }

    fun endDate(date: Date): Date {
        val newDate = Date()
        val argsDate = date.date
        newDate.month = when (argsDate) {
            1 -> newDate.month
            else -> newDate.month + 1
        }

        newDate.date = when (argsDate) {
            1 -> this.getLastDayOfMonth()
            else -> argsDate - 1
        }
        return newDate
    }

    fun startToEndToString(startDate: Date, endDate: Date): String {
        return StringBuilder()
            .append(dateFormatContainYear.format(startDate))
            .append(" - ")
            .append(dateFormatContainYear.format(endDate))
            .toString()
    }

    fun startToEndToStringNoYear(startDate: Date, endDate: Date): String {
        return StringBuilder()
            .append(dateFormatNoYear.format(startDate))
            .append(" - ")
            .append(dateFormatNoYear.format(endDate))
            .toString()
    }

    fun calculateDate(startDate: Date, endDate: Date): Int {
        return ((endDate.time / MILLIS) - (startDate.time / MILLIS) + 1).toInt()
    }

    fun stringToDate(inputDate: String): Date{
        return Date(inputDate.substring(0,4).toInt()-1900,
            inputDate.substring(5,7).toInt()-1,
            inputDate.substring(8).toInt())
    }
}