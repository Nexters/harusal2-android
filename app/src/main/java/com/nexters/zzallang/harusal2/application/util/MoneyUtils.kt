package com.nexters.zzallang.harusal2.application.util

object MoneyUtils {
    private val UNIT = arrayOf("", "만", "억", "조", "경", "해")

    fun convertString(money: Long): String {
        if (money == 0L) {
            return "0원"
        }

        var money = money
        val korean = StringBuilder()
        val subKorean = StringBuilder()
        var unit = 0
        var isFirst = true
        var zeroCount = 0;

        while (money > 0) {
            val digit = (money % 10).toInt()
            money /= 10

            if (unit % 4 == 0) {
                isFirst = true
                zeroCount = 0
            }

            if (digit == 0) {
                zeroCount++
                unit++
                continue
            }

            subKorean.append(digit).append("0".repeat(zeroCount))
            if (isFirst) {
                subKorean.append(UNIT[unit / 4])
                isFirst = false;
            }

            korean.insert(0, subKorean)
            subKorean.setLength(0)
            unit++
            zeroCount = 0
        }
        return korean.append("원").toString()
    }
}