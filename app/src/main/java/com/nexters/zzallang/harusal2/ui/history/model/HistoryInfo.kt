package com.nexters.zzallang.harusal2.ui.history.model

data class HistoryInfo(
    val money: Int = 0,
    val period: String = ""
) : BaseHistoryRecyclerItem()