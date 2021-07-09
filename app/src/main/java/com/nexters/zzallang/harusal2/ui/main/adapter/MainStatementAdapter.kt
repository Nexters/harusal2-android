package com.nexters.zzallang.harusal2.ui.main.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.application.util.NumberUtils
import com.nexters.zzallang.harusal2.databinding.ItemMainDateBinding
import com.nexters.zzallang.harusal2.databinding.ItemMainStatementBinding
import com.nexters.zzallang.harusal2.databinding.ItemMainStatementEmptyBinding
import com.nexters.zzallang.harusal2.ui.main.model.BaseMainRecyclerViewStatementItem
import com.nexters.zzallang.harusal2.ui.main.model.MainDate
import com.nexters.zzallang.harusal2.ui.main.model.MainStatement
import com.nexters.zzallang.harusal2.ui.statement.edit.StatementDetailActivity
import com.nexters.zzallang.harusal2.ui.statement.register.AddStatementActivity

class MainStatementAdapter : RecyclerView.Adapter<MainStatementAdapter.BaseViewHolder>() {
    private val statementList = arrayListOf<MainStatement>()
    private val date = MainDate(DateUtils.toTodayString())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        when (viewType) {
            0 -> MainDateViewHolder(
                DataBindingUtil
                    .inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_main_date,
                        parent,
                        false
                    )
            )
            1 -> MainEmptyStatementViewHolder(
                DataBindingUtil
                    .inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_main_statement_empty,
                        parent,
                        false
                    )
            )
            else ->
                MainStatementViewHolder(
                    DataBindingUtil
                        .inflate(
                            LayoutInflater.from(parent.context),
                            R.layout.item_main_statement,
                            parent,
                            false
                        )
                )
        }

    // 오늘의 기록이 있기 때문에 +1 해줌
    override fun getItemCount(): Int = if (statementList.size == 0) 2 else statementList.size + 1

    // 0은 오늘의 기록 표기, 1은 statement 표기
    override fun getItemViewType(position: Int): Int = if (position == 0) {
        0
    } else if (statementList.size == 0){
        1
    } else {
        2
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        // position 0은 날짜로 쓰고있기 때문에 statement 를 사용하려면 -1을 하여 사용해야한다.
        holder.onBind(when (position) {
            0 -> date
            else -> if (statementList.size == 0) {
                MainStatement()
            } else {
                statementList[position - 1]
            }
        })
    }

    fun clearMainStatementList() {
        statementList.clear()
    }

    private fun addStatement(item: MainStatement) {
        statementList.add(item)
        notifyDataSetChanged()
    }

    fun addStatements(items: List<MainStatement>) {
        for (item in items) {
            addStatement(item)
        }
        notifyDataSetChanged()
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
            binding.tvMoney.text = "${NumberUtils.decimalFormat.format(item.money)}원"
            binding.tvContent.text = item.content


            binding.root.setOnClickListener {
                val intent = Intent(it.context, StatementDetailActivity::class.java)
                intent.putExtra("statementId", item.id)

                it.context.startActivity(intent)
            }
        }
    }

    class MainEmptyStatementViewHolder(private val binding: ItemMainStatementEmptyBinding): BaseViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                it.context.startActivity(Intent(it.context, AddStatementActivity::class.java))
            }
        }

        override fun onBind(item: BaseMainRecyclerViewStatementItem) {}
    }
}