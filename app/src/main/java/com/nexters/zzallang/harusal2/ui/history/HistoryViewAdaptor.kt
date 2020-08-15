package com.nexters.zzallang.harusal2.ui.history

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.databinding.ItemHistoryCardBinding
import com.nexters.zzallang.harusal2.databinding.ItemHistoryTitleBinding
import com.nexters.zzallang.harusal2.ui.history.model.BaseHistoryRecyclerItem
import com.nexters.zzallang.harusal2.ui.history.model.HistoryStatement
import com.nexters.zzallang.harusal2.ui.history.model.HistoryTitle

class HistoryViewAdaptor(private val context: Context) :
    RecyclerView.Adapter<HistoryViewAdaptor.BaseViewHolder>() {

    private val histories = arrayListOf<BaseHistoryRecyclerItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        when (viewType) {
            0 -> TitleTypeViewHolder(
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
            0, 2 -> 0
            else -> 1
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(histories[position])
    }

    fun clearHistoryStatement() {
        histories.clear()
    }

    fun addStatements(items: ArrayList<BaseHistoryRecyclerItem>) {
        histories.addAll(items)
    }

    abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun onBind(item: BaseHistoryRecyclerItem)
    }

    inner class TitleTypeViewHolder(private val binding: ItemHistoryTitleBinding) :
        BaseViewHolder(binding.root) {
        override fun onBind(item: BaseHistoryRecyclerItem) {
            item as HistoryTitle
            binding.tvTitle.text = item.title
        }
    }

    inner class CardTypeViewHolder(private val binding: ItemHistoryCardBinding) :
        BaseViewHolder(binding.root) {
        override fun onBind(item: BaseHistoryRecyclerItem) {
            item as HistoryStatement
            binding.tvDay.text = item.day.toString()
            binding.tvIncome.text = item.income
            binding.tvSpending.text = item.spending
        }
    }
}