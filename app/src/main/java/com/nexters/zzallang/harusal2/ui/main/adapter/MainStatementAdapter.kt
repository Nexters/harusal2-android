package com.nexters.zzallang.harusal2.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.databinding.ItemMainStatementBinding
import com.nexters.zzallang.harusal2.ui.main.model.MainStatement

class MainStatementAdapter(private val context: Context) : RecyclerView.Adapter<MainStatementAdapter.MainStatementViewHolder>() {
    private val statementList = arrayListOf<MainStatement>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainStatementViewHolder =
        MainStatementViewHolder(
            DataBindingUtil
                .inflate(
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                    R.layout.item_main_statement,
                    parent,
                    false
                )
        )

    override fun getItemCount(): Int = statementList.size

    override fun onBindViewHolder(holder: MainStatementViewHolder, position: Int) {
        holder.onBind(statementList[position])
    }

    fun clearMainStatementList() {
        statementList.clear()
    }

    fun addStatement(item: MainStatement) {
        statementList.add(item)
    }

    fun addStatements(items: ArrayList<MainStatement>) {
        for (item in items) {
            addStatement(item)
        }
    }

    inner class MainStatementViewHolder(private val binding: ItemMainStatementBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: MainStatement) {
            binding.tvMoney.text = item.money.toString().plus("원")
            binding.tvContent.text = item.content
        }
    }
}