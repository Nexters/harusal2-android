package com.nexters.zzallang.harusal2.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.databinding.ItemMainDateBinding
import com.nexters.zzallang.harusal2.databinding.ItemMainStatementBinding
import com.nexters.zzallang.harusal2.ui.main.model.BaseMainRecyclerViewStatementItem
import com.nexters.zzallang.harusal2.ui.main.model.MainDate
import com.nexters.zzallang.harusal2.ui.main.model.MainStatement

class MainStatementAdapter(private val context: Context) : RecyclerView.Adapter<MainStatementAdapter.BaseViewHolder>() {
    private val statementList = arrayListOf<MainStatement>()
    /* TODO: date set, get 만들자 */
    private val date = MainDate("2020.07.20")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        if (viewType == 0) {
            MainDateViewHolder(
                DataBindingUtil
                    .inflate(
                        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                        R.layout.item_main_date,
                        parent,
                        false
                    )
            )
        } else {
            MainStatementViewHolder(
                DataBindingUtil
                    .inflate(
                        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                        R.layout.item_main_statement,
                        parent,
                        false
                    )
            )
        }

    override fun getItemCount(): Int = statementList.size

    // 0은 오늘의 기록 표기, 1은 statement 표기
    override fun getItemViewType(position: Int): Int = if (position == 0) 0 else 1

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        // position 0은 날짜로 쓰고있기 때문에 statement 를 사용하려면 -1을 하여 사용해야한다.
        holder.onBind(when (position) {
            0 -> date
            else -> statementList[position - 1]
        })
    }

    fun clearMainStatementList() {
        statementList.clear()
    }

    private fun addStatement(item: MainStatement) {
        statementList.add(item)
    }

    fun addStatements(items: ArrayList<MainStatement>) {
        for (item in items) {
            addStatement(item)
        }
    }

    abstract class BaseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        abstract fun onBind(item: BaseMainRecyclerViewStatementItem)
    }

    class MainDateViewHolder(private val binding: ItemMainDateBinding): BaseViewHolder(binding.root) {
        override fun onBind(item: BaseMainRecyclerViewStatementItem) {
            item as MainDate
            binding.tvDate.text = item.date
        }
    }

    class MainStatementViewHolder(private val binding: ItemMainStatementBinding): BaseViewHolder(binding.root) {
        override fun onBind(item: BaseMainRecyclerViewStatementItem) {
            item as MainStatement
            binding.tvMoney.text = item.money.toString().plus("원")
            binding.tvContent.text = item.content
        }
    }
}