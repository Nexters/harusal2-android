package com.nexters.zzallang.harusal2.ui.history.viewholder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nexters.zzallang.harusal2.ui.history.model.BaseHistoryRecyclerItem

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun onBind(item: BaseHistoryRecyclerItem, position: Int, context: Context)
}