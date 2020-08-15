package com.nexters.zzallang.harusal2.ui.history

data class HistoryViewTypeIndex(val type: Int, val text: String, val data: Int, val contentString: String?) {
    companion object {
        const val TITLE = 0
        const val CARD = 1
    }
}