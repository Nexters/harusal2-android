package com.nexters.zzallang.harusal2.ui.history.menu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.data.entity.Budget
import com.nexters.zzallang.harusal2.ui.history.HistoryActivity

class HistoryMenuAdapter(private val historyActivity: HistoryActivity) : BaseAdapter() {
    private val budgets = arrayListOf<Budget>()
    var currentPosition: Int = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        var viewHolder: HistoryMenuViewHolder
        val budget = budgets[position]

        if (convertView == null) {
            val layoutInflater =
                parent?.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = layoutInflater.inflate(R.layout.item_history_menu, parent, false)
            viewHolder =
                HistoryMenuViewHolder()
            viewHolder.tvPeriod = view.findViewById(R.id.tv_menu_period)
            viewHolder.ivRadio = view.findViewById(R.id.iv_menu_radio)

            view.tag = viewHolder
        } else {
            viewHolder = convertView.tag as HistoryMenuViewHolder
            view = convertView
        }

        viewHolder.tvPeriod?.text =
            DateUtils.startToEndToString(budget.startDate, budget.endDate)

        if(position == currentPosition){
            viewHolder.ivRadio?.setImageResource(R.drawable.ic_radio_btn_on_24)
        } else{
            viewHolder.ivRadio?.setImageResource(R.drawable.ic_radio_btn_off_24)
        }

        view.setOnClickListener {
            val thisViewHolder = it.tag as HistoryMenuViewHolder
            thisViewHolder.ivRadio?.setImageResource(R.drawable.ic_radio_btn_on_24)
            historyActivity.clickOnItem(position)
            currentPosition = position
        }

        return view
    }

    override fun getItem(position: Int): Any {
        return budgets[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return budgets.size
    }

    fun clear() {
        this.budgets.clear()
    }

    fun addBudget(budgets: List<Budget>) {
        this.budgets.addAll(budgets)
    }
}