package com.nexters.zzallang.harusal2.ui.history.viewholder

import android.content.Context
import com.nexters.zzallang.harusal2.databinding.ItemHistoryTitleBinding
import com.nexters.zzallang.harusal2.ui.history.model.BaseHistoryRecyclerItem
import com.nexters.zzallang.harusal2.ui.history.model.HistoryTitle

class TitleTypeViewHolder(private val binding: ItemHistoryTitleBinding) :
    BaseViewHolder(binding.root) {

    override fun onBind(item: BaseHistoryRecyclerItem, position: Int, context: Context) {
        item as HistoryTitle
        binding.tvTitle.text = item.title
    }
}