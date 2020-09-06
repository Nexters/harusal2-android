package com.nexters.zzallang.harusal2.ui.history.model

data class HistoryCard(
    val day: Int,
    val income: Int = 0,
    val spending: Int = 0,
    val historyStatements: List<HistoryStatement>? = null
) : BaseHistoryRecyclerItem()