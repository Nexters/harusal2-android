package com.nexters.zzallang.harusal2.ui.main.model

data class MainStatement(
    val id: Long = 0L,
    val money: Int = 0,
    val content: String = ""
) : BaseMainRecyclerViewStatementItem()