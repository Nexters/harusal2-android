package com.nexters.zzallang.harusal2.usecase

import com.nexters.zzallang.harusal2.constant.SpendState

class GetSpentMoneyStatusUseCase {
    fun getSpentMoneyStatus(todayBudget: Int, remainMoney: Int): SpendState =
        when {
            (todayBudget * (7f / 10)) < remainMoney -> {
                SpendState.FLEX
            }
            (todayBudget * (1f / 10)) < remainMoney -> {
                SpendState.CLAP
            }
            (todayBudget * (4f / 10)) < remainMoney -> {
                SpendState.EMBARRASSED
            }
            (todayBudget * (8f / 10)) < remainMoney -> {
                SpendState.CRY
            }
            else -> {
                SpendState.VOLCANO
            }
        }
}