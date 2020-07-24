package com.nexters.zzallang.harusal2.application.util

object MoneyUtils {
    private val DIGIT = arrayOf("", "일", "이", "삼", "사", "오", "육", "칠", "팔", "구", "십")
    private val UNIT = arrayOf("", "십", "백", "천")
    private val TEMP = arrayOf("", "만", "억", "조", "경", "해")

    fun convertString(money: Long): String {
        if (money == 0L) {
            return "영원"
        }

        var money = money
        val korean = StringBuilder()
        val subKorean = StringBuilder()
        var unit = 0
        var isFirst = true

        while (money > 0) {
            val digit = (money % 10).toInt()
            if (unit % 4 == 0) {
                isFirst = true
            }

            if (DIGIT[digit] != "") {
                subKorean.append(DIGIT[digit] + UNIT[unit % 4])
                if (isFirst) {
                    subKorean.append(TEMP[unit / 4])
                    isFirst = false;
                }
            }

            korean.insert(0, subKorean)
            subKorean.setLength(0)
            money /= 10
            unit++
        }
        return korean.append("원").toString()
    }
}