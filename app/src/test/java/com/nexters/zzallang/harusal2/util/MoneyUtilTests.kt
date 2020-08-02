package com.nexters.zzallang.harusal2.util

import com.nexters.zzallang.harusal2.application.util.MoneyUtils.convertString
import junit.framework.Assert.assertEquals
import org.junit.Test

class MoneyUtilTests {
    @Test
    fun 돈_한글_변환_0원() {
        assertEquals("0원", convertString(0L))
    }

    @Test
    fun 돈_한글_변환_1억5000만원() {
        assertEquals("1억5000만원", convertString(150000000L))
    }

    @Test
    fun 돈_한글_변환_1억2345만6789원() {
        assertEquals("1억2345만6789원", convertString(123456789L))
    }

    @Test
    fun 돈_한글_변환_90만원() {
        assertEquals("90만원", convertString(900000L))
    }

    @Test
    fun 돈_한글_변환_350만900원() {
        assertEquals("350만900원", convertString(3500900L))
    }
}