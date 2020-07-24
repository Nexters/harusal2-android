package com.nexters.zzallang.harusal2.util

import com.nexters.zzallang.harusal2.application.util.MoneyUtils.convertString
import junit.framework.Assert.assertEquals
import org.junit.Test

class MoneyUtilTests {
    @Test
    fun 돈_한글_변환_영원() {
        assertEquals("영원", convertString(0L))
    }

    @Test
    fun 돈_한글_변환_일억오천만원() {
        assertEquals("일억오천만원", convertString(150000000L))
    }

    @Test
    fun 돈_한글_변환() {
        assertEquals("일억이천삼백사십오만육천칠백팔십구원", convertString(123456789L))
    }

    @Test
    fun 돈_한글_변환_구십만원() {
        assertEquals("구십만원", convertString(900000L))
    }

    @Test
    fun 돈_한글_변환_삼백오십만구백원() {
        assertEquals("삼백오십만구백원", convertString(3500900L))
    }
}