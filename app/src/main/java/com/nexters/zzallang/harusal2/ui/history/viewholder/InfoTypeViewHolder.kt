package com.nexters.zzallang.harusal2.ui.history.viewholder

import android.content.Context
import com.airbnb.lottie.LottieDrawable
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.util.NumberUtils
import com.nexters.zzallang.harusal2.constant.SpendState
import com.nexters.zzallang.harusal2.databinding.ItemHistoryInfoBinding
import com.nexters.zzallang.harusal2.ui.history.model.BaseHistoryRecyclerItem
import com.nexters.zzallang.harusal2.ui.history.model.HistoryInfo

class InfoTypeViewHolder(private val binding: ItemHistoryInfoBinding) :
    BaseViewHolder(binding.root) {
    override fun onBind(item: BaseHistoryRecyclerItem, position: Int, context: Context) {
        item as HistoryInfo

        val backgroundColor: Int
        val emojiName: String
        var textColor: Int = context.getColor(R.color.white)
        when (item.state) {
            SpendState.FLEX -> {
                backgroundColor = R.color.bg_blue
                emojiName = "flex_coin.json"
            }
            SpendState.CLAP -> {
                backgroundColor = R.color.bg_mint
                emojiName = "clap_coin.json"
            }
            SpendState.DEFAULT -> {
                backgroundColor = R.color.white
                emojiName = "default_coin.json"
                textColor = context.getColor(R.color.default_txt)
            }
            SpendState.EMBARRASSED -> {
                backgroundColor = R.color.bg_yellow
                emojiName = "embassed_coin.json"
            }
            SpendState.CRY -> {
                backgroundColor = R.color.bg_orange
                emojiName = "cry_coin.json"
            }
            else -> {
                backgroundColor = R.color.bg_red
                emojiName = "volcano_coin_draft2.json"
            }
        }

        binding.ivHistoryEmoji.setAnimation(emojiName)
        binding.ivHistoryEmoji.playAnimation()

        val color = context.getColor(backgroundColor)
        binding.layoutHistoryStatement.setBackgroundColor(color)

        binding.tvRemainMoney.text =
            """${NumberUtils.decimalFormat.format(item.budget + item.totalAmount)}원"""

        binding.tvUsedMoney.text =
            """누적 사용금액 ${NumberUtils.decimalFormat.format(item.totalAmount)}원"""

        binding.ivHistoryEmoji.repeatCount = LottieDrawable.INFINITE

        binding.tvDescription.setTextColor(textColor)
        binding.tvRemainMoney.setTextColor(textColor)
        binding.tvUsedMoney.setTextColor(textColor)
    }
}