package com.nexters.zzallang.harusal2.ui.history.model

import com.nexters.zzallang.harusal2.ui.main.SpendState

data class HistoryInfo(
    val money: Int = 0,
    val period: String = "",
    val state: SpendState = SpendState.DEFAULT
) : BaseHistoryRecyclerItem()