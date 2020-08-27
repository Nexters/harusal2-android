package com.nexters.zzallang.harusal2.ui.history.model

data class HistoryCard(
    val day: Int,
    val income: String = "0원",
    val spending: String = "0원",
    val historyStatements: List<HistoryStatement>? = null
) : BaseHistoryRecyclerItem()