package com.nexters.zzallang.harusal2.application.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateUtils {
    private const val NO_YEAR_FORMAT = "MM.dd"
    private const val YEAR_FORMAT = "yyyy.MM.dd"
    fun getLastDayOfMonth(): Int {
        return getLastDayOfMonthAfter(0)
    }

    fun getLastDayOfMonthAfter(month: Int): Int {
        val date = LocalDate.now().plusMonths(month.toLong())
        return date.lengthOfMonth()
    }

    fun endDate(date: LocalDate): LocalDate {
        val month = when (date.dayOfMonth) {
            1 -> date.month
            else -> date.plusMonths(1).month
        }

        val day = when (date.dayOfMonth) {
            1 -> this.getLastDayOfMonth()
            else -> date.dayOfMonth - 1
        }
        return LocalDate.of(date.year, month, day)
    }

    fun startToEndToString(startDate: LocalDate, endDate: LocalDate): String {
        return StringBuilder()
            .append(this.toString(startDate, YEAR_FORMAT))
            .append(" - ")
            .append(this.toString(endDate, YEAR_FORMAT))
            .toString()
    }

    fun startToEndToStringNoYear(startDate: LocalDate, endDate: LocalDate): String {
        return StringBuilder()
            .append(this.toString(startDate, NO_YEAR_FORMAT))
            .append(" - ")
            .append(this.toString(endDate, NO_YEAR_FORMAT))
            .toString()
    }

    fun calculateDate(startDate: LocalDate, endDate: LocalDate): Int {
        return Math.abs((endDate.toEpochDay() - startDate.toEpochDay()).toInt()) + 1
    }

    fun toTodayString(): String {
        return this.toString(LocalDate.now(), YEAR_FORMAT)
    }

    fun stringToDate(inputDate: String): LocalDate{
        val splitFormat = inputDate.split(".")
        return LocalDate.of(splitFormat[0].toInt(),
            splitFormat[1].toInt(),
            splitFormat[2].toInt())
    }

    fun toString(localDate: LocalDate, pattern:String):String{
        return localDate.format(DateTimeFormatter.ofPattern(pattern))
    }
}