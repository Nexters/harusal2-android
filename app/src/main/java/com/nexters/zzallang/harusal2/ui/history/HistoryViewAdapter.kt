package com.nexters.zzallang.harusal2.ui.history

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.ui.history.model.BaseHistoryRecyclerItem
import com.nexters.zzallang.harusal2.ui.history.viewholder.BaseViewHolder
import com.nexters.zzallang.harusal2.ui.history.viewholder.CardTypeViewHolder
import com.nexters.zzallang.harusal2.ui.history.viewholder.InfoTypeViewHolder
import com.nexters.zzallang.harusal2.ui.history.viewholder.TitleTypeViewHolder


class HistoryViewAdapter(private val context: Context) :
        RecyclerView.Adapter<BaseViewHolder>() {
    private val histories = arrayListOf<BaseHistoryRecyclerItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
            when (viewType) {
                0 -> InfoTypeViewHolder(
                        DataBindingUtil
                                .inflate(
                                        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                                        R.layout.item_history_info,
                                        parent,
                                        false
                                )
                )
                1 -> TitleTypeViewHolder(
                        DataBindingUtil
                                .inflate(
                                        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                                        R.layout.item_history_title,
                                        parent,
                                        false
                                )
                )
                else -> CardTypeViewHolder(
                        DataBindingUtil
                                .inflate(
                                        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                                        R.layout.item_history_card,
                                        parent,
                                        false
                                )
                )
            }

    override fun getItemCount(): Int {
        return histories.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> 0
            1, 3 -> 1
            else -> 2
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(histories[position], position, context)
    }

    fun clearHistoryStatement() {
        histories.clear()
    }

    fun addStatements(items: List<BaseHistoryRecyclerItem>) {
        histories.addAll(items)
    }
}