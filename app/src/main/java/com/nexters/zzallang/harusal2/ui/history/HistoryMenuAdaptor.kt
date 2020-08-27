package com.nexters.zzallang.harusal2.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.data.entity.Budget
import kotlinx.android.synthetic.main.item_history_menu.view.*

class HistoryMenuAdaptor(
    private val mDataset: List<Budget>,
    internal var recyclerViewItemClickListener: RecyclerViewItemClickListener
) : RecyclerView.Adapter<HistoryMenuAdaptor.HistoryMenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): HistoryMenuViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_history_menu, parent, false)
        return HistoryMenuViewHolder(v)

    }

    override fun onBindViewHolder(historyMenuViewHolder: HistoryMenuViewHolder, i: Int) {
        historyMenuViewHolder.tvPeriod.text =
            DateUtils.startToEndToString(mDataset[i].startDate, mDataset[i].endDate)
    }

    override fun getItemCount(): Int {
        return mDataset.size
    }


    inner class HistoryMenuViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        var tvPeriod: TextView = v.tv_period
        var prevPosition = 0

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            v.iv_radio.setImageResource(R.drawable.ic_radio_btn_on_24)
            recyclerViewItemClickListener.clickOnItem(mDataset[this.adapterPosition])
            prevPosition = this.adapterPosition
        }
    }

    interface RecyclerViewItemClickListener {
        fun clickOnItem(budget: Budget)
    }
}
