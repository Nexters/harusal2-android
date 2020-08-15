package com.nexters.zzallang.harusal2.ui.history.model

data class HistoryStatement(
    val day: Int = 0,
    val income: String = "",
    val spending: String = ""
) : BaseHistoryRecyclerItem()