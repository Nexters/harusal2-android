package com.nexters.zzallang.harusal2.ui.main

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
    }
}