package com.nexters.zzallang.harusal2.ui.history

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.databinding.ItemHistoryCardBinding
import com.nexters.zzallang.harusal2.databinding.ItemHistoryInfoBinding
import com.nexters.zzallang.harusal2.databinding.ItemHistoryTitleBinding
import com.nexters.zzallang.harusal2.ui.budget.register.StartDayDefaultRegisterActivity
import com.nexters.zzallang.harusal2.ui.history.model.*


class HistoryViewAdaptor(private val context: Context) :
    RecyclerView.Adapter<HistoryViewAdaptor.BaseViewHolder>() {
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
        holder.onBind(histories[position], position)
    }

    fun clearHistoryStatement() {
        histories.clear()
    }

    fun addStatements(items: ArrayList<BaseHistoryRecyclerItem>) {
        histories.addAll(items)
    }

    abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun onBind(item: BaseHistoryRecyclerItem, position: Int)
    }

    inner class CardTypeViewHolder(private val binding: ItemHistoryCardBinding) :
        BaseViewHolder(binding.root) {

        override fun onBind(item: BaseHistoryRecyclerItem, position: Int) {
            val mInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            item as HistoryCard

            if(position == 2 && binding.layoutHistoryItem.visibility == View.GONE){
                binding.layoutHistoryItem.visibility = View.VISIBLE
                binding.btnExpand.setImageResource(R.drawable.ic_btn_dropup_24)
            }

            binding.layoutHistoryItem.removeAllViews()
            item.historyStatements?.forEach { historyStatement: HistoryStatement ->
                val layout = mInflater.inflate(
                    R.layout.item_history_statement,
                    binding.layoutHistoryItem,
                    false
                ) as ConstraintLayout
                layout.findViewById<TextView>(R.id.tv_money).text =
                    historyStatement.money.toString()
                layout.findViewById<TextView>(R.id.tv_content).text = historyStatement.content

                //TODO : Activity 변경
                layout.findViewById<ImageButton>(R.id.btn_statement_detail).setOnClickListener {
                    val intent = Intent(context, StartDayDefaultRegisterActivity::class.java)
                    context.startActivity(intent)
                }

                binding.layoutHistoryItem.addView(layout)
            }

            binding.tvDay.text = item.day.toString()
            binding.tvIncome.text = item.income
            binding.tvSpending.text = item.spending
            binding.btnExpand.setOnClickListener {
                when (binding.layoutHistoryItem.visibility) {
                    View.GONE -> {
                        expand(binding.layoutHistoryItem)
                        binding.btnExpand.setImageResource(R.drawable.ic_btn_dropup_24)

                    }
                    else -> {
                        collapse(binding.layoutHistoryItem)
                        binding.btnExpand.setImageResource(R.drawable.ic_btn_dropdown_24)
                    }
                }
            }
        }

        private fun expand(mLinearLayout: LinearLayout) {
            //set Visible
            mLinearLayout.visibility = View.VISIBLE
            val widthSpec = View.MeasureSpec.makeMeasureSpec(
                0,
                View.MeasureSpec.UNSPECIFIED
            )
            val heightSpec = View.MeasureSpec.makeMeasureSpec(
                0,
                View.MeasureSpec.UNSPECIFIED
            )
            mLinearLayout.measure(widthSpec, heightSpec)
            val mAnimator: ValueAnimator =
                slideAnimator(mLinearLayout, 0, mLinearLayout.measuredHeight)
            mAnimator.start()
        }

        private fun collapse(mLinearLayout: LinearLayout) {
            val finalHeight: Int = mLinearLayout.height
            val mAnimator: ValueAnimator = slideAnimator(mLinearLayout, finalHeight, 0)

            mAnimator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator) {
                    mLinearLayout.visibility = View.GONE
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }
            })

            mAnimator.start()
        }

        private fun slideAnimator(
            mLinearLayout: LinearLayout,
            start: Int,
            end: Int
        ): ValueAnimator {
            val animator = ValueAnimator.ofInt(start, end)

            animator.addUpdateListener { valueAnimator ->
                val value = valueAnimator.animatedValue as Int
                val layoutParams: ViewGroup.LayoutParams = mLinearLayout.layoutParams
                layoutParams.height = value
                mLinearLayout.layoutParams = layoutParams
            }
            return animator
        }
    }

    inner class TitleTypeViewHolder(private val binding: ItemHistoryTitleBinding) :
        BaseViewHolder(binding.root) {

        override fun onBind(item: BaseHistoryRecyclerItem, position: Int) {
            item as HistoryTitle
            binding.tvTitle.text = item.title
        }
    }

    inner class InfoTypeViewHolder(private val binding: ItemHistoryInfoBinding) :
        BaseViewHolder(binding.root) {
        override fun onBind(item: BaseHistoryRecyclerItem, position: Int) {
            item as HistoryInfo
            binding.tvPeriod.text = item.period
            binding.tvMoney.text = item.money.toString() + "원"
        }
    }
}