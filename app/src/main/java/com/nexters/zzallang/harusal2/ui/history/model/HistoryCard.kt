package com.nexters.zzallang.harusal2.ui.history.model

import com.nexters.zzallang.harusal2.data.entity.Statement

data class HistoryCard(
    val day: Int = 0,
    val income: String = "",
    val spending: String = "",
    val historyStatements: List<HistoryStatement>?
) : BaseHistoryRecyclerItem()