package com.nexters.zzallang.harusal2.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.data.entity.Budget
import com.nexters.zzallang.harusal2.ui.history.model.BaseHistoryRecyclerItem
import kotlinx.android.synthetic.main.item_history_menu.view.*

class HistoryMenuAdaptor(
    internal var recyclerViewItemClickListener: RecyclerViewItemClickListener
) : RecyclerView.Adapter<HistoryMenuAdaptor.HistoryMenuViewHolder>() {
    var previous: View? = null
    private val budgets = arrayListOf<Budget>()

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): HistoryMenuViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_history_menu, parent, false)
        val viewHolder = HistoryMenuViewHolder(v)

        if(previous == null) {
            viewHolder.ivRadio.setImageResource(R.drawable.ic_radio_btn_on_24)
            previous = viewHolder.itemView
        }
        return viewHolder
    }

    override fun onBindViewHolder(historyMenuViewHolder: HistoryMenuViewHolder, i: Int) {
        historyMenuViewHolder.tvPeriod.text =
            DateUtils.startToEndToString(budgets[i].startDate, budgets[i].endDate)
    }

    override fun getItemCount(): Int {
        return budgets.size
    }

    fun clear(){
        this.budgets.clear()
    }

    fun addBudget(budgets: List<Budget>){
        this.budgets.addAll(budgets)
    }

    inner class HistoryMenuViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        var tvPeriod: TextView = v.tv_period
        var ivRadio:ImageView = v.iv_radio
        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            previous?.iv_radio?.setImageResource(R.drawable.ic_radio_btn_off_24)
            v.iv_radio.setImageResource(R.drawable.ic_radio_btn_on_24)
            recyclerViewItemClickListener.clickOnItem(budgets[this.adapterPosition])
            previous = v
        }
    }

    interface RecyclerViewItemClickListener {
        fun clickOnItem(budget: Budget)
    }
}
