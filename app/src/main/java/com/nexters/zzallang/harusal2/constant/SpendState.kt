package com.nexters.zzallang.harusal2.constant

import com.nexters.zzallang.harusal2.R

enum class SpendState {
    FLEX,
    CLAP,
    DEFAULT,
    EMBARRASSED,
    CRY,
    VOLCANO;

    companion object {
        fun getBackgroundColor(state: SpendState): Int =
            when (state) {
                FLEX -> {
                    R.color.bg_blue
                }
                CLAP -> {
                    R.color.bg_mint
                }
                DEFAULT -> {
                    R.color.white
                }
                EMBARRASSED -> {
                    R.color.bg_yellow
                }
                CRY -> {
                    R.color.bg_orange
                }
                else -> {
                    R.color.bg_red
                }
            }

        fun getMultiplyColor(state: SpendState): Int =
            when(state){
                CLAP -> {
                    R.drawable.widget_text_bg_mint
                }
                EMBARRASSED -> {
                    R.drawable.widget_text_bg_yellow
                }
                CRY -> {
                    R.drawable.widget_text_bg_orange
                }
                VOLCANO -> {
                    R.drawable.widget_text_bg_red
                }
                else -> {
                    R.drawable.widget_text_bg_blue
                }
            }

        fun getWidgetMessage(state: SpendState): Int =
            when(state){
                CLAP -> {
                    R.string.text_clap_widget
                }
                EMBARRASSED -> {
                    R.string.text_embarrassed_widget
                }
                CRY -> {
                    R.string.text_cry_widget
                }
                VOLCANO -> {
                    R.string.text_volcano_widget
                }
                else -> {
                    R.string.text_flex_widget
                }
            }
    }
}