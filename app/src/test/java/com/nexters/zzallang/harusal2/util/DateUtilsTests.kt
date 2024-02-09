package com.nexters.zzallang.harusal2.util

import com.nexters.zzallang.harusal2.application.util.DateUtils
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class DateUtilsTests {

    @Test
    fun endDate_2월28일_일경우_3월27일_이어야한다() {
        assertEquals(LocalDate.of(2024, 3, 27),
            DateUtils.getBudgetEndDate(LocalDate.of(2024,2,28)))
    }

    @Test
    fun endDate_윤년_2월조회() {
        assertEquals(LocalDate.of(2024, 3, 27),
            DateUtils.getBudgetEndDate(LocalDate.of(2024,2,28)))
    }

    @Test
    fun endDate_윤년_30일이마지막인_1일조회() {
        assertEquals(LocalDate.of(2024, 4, 30),
            DateUtils.getBudgetEndDate(LocalDate.of(2024,4,1)))
    }

    @Test
    fun endDate_윤년_31일이마지막인_1일조회() {
        assertEquals(LocalDate.of(2024, 5, 31),
            DateUtils.getBudgetEndDate(LocalDate.of(2024,5,1)))
    }

    @Test
    fun endDate_해가_넘어가도_정상조회되야한다_20231203() {
        assertEquals(LocalDate.of(2024, 1, 29),
            DateUtils.getBudgetEndDate(LocalDate.of(2023,12,30)))
    }

    @Test
    fun endDate_해가_넘어가기직전_정상조회_20231201() {
        assertEquals(LocalDate.of(2023, 12, 31),
            DateUtils.getBudgetEndDate(LocalDate.of(2023,12,1)))
    }

    @Test
    fun endDate_해가_넘어가도_정상조회_20231215() {
        assertEquals(LocalDate.of(2024, 1, 14),
            DateUtils.getBudgetEndDate(LocalDate.of(2023,12,15)))
    }
}