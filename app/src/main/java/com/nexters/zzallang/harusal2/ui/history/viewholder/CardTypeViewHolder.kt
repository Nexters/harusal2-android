package com.nexters.zzallang.harusal2.ui.history.viewholder

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.util.NumberUtils
import com.nexters.zzallang.harusal2.databinding.ItemHistoryCardBinding
import com.nexters.zzallang.harusal2.ui.history.model.BaseHistoryRecyclerItem
import com.nexters.zzallang.harusal2.ui.history.model.HistoryCard
import com.nexters.zzallang.harusal2.ui.history.model.HistoryStatement
import com.nexters.zzallang.harusal2.ui.statement.edit.StatementDetailActivity

class CardTypeViewHolder(private val binding: ItemHistoryCardBinding) : BaseViewHolder(binding.root) {

    override fun onBind(item: BaseHistoryRecyclerItem, position: Int, context: Context) {
        val mInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        item as HistoryCard

        if (binding.layoutHistoryItem.visibility == View.GONE) {
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

            layout.findViewById<TextView>(R.id.tv_money)
                .setTextColor(context.getColor(R.color.default_txt))

            layout.findViewById<TextView>(R.id.tv_money).text =
                if (historyStatement.money > 0) {
                    "+"
                } else {
                    ""
                }.plus(NumberUtils.decimalFormat.format(historyStatement.money))
                    .plus("원")
            layout.findViewById<TextView>(R.id.tv_content).text = historyStatement.content
            layout.setOnClickListener {
                val intent = Intent(context, StatementDetailActivity::class.java)
                intent.putExtra("statementId", historyStatement.id)
                context.startActivity(intent)
            }

            binding.layoutHistoryItem.addView(layout)
        }

        binding.tvDay.text = item.day.toString()

        binding.tvIncome.text = when {
            item.income > 0 -> "+"
            else -> ""
        } + """${NumberUtils.decimalFormat.format(item.income)}원"""

        binding.tvSpending.text = "${NumberUtils.decimalFormat.format(item.spending)}원"

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