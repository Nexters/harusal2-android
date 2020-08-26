package com.nexters.zzallang.harusal2.ui.history.model

data class HistoryCard(
    val day: Int,
    val income: String = "",
    val spending: String = "",
    val historyStatements: List<HistoryStatement>? = null
) : BaseHistoryRecyclerItem()