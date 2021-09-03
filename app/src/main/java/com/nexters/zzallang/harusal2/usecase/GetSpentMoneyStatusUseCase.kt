package com.nexters.zzallang.harusal2.usecase

import com.nexters.zzallang.harusal2.constant.SpendState

class GetSpentMoneyStatusUseCase {
    fun getSpentMoneyStatus(livingExpenses: Int, remainMoney: Int): SpendState =
        when {
            (livingExpenses * (7f / 10)) < remainMoney -> {
                SpendState.FLEX
            }
            (livingExpenses * (1f / 10)) < remainMoney -> {
                SpendState.CLAP
            }
            (livingExpenses * (4f / 10)) < remainMoney -> {
                SpendState.EMBARRASSED
            }
            (livingExpenses * (8f / 10)) < remainMoney -> {
                SpendState.CRY
            }
            else -> {
                SpendState.VOLCANO
            }
        }
}