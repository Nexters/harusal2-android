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
                    R.color.colorPointBlueBackground
                }
                CLAP -> {
                    R.color.colorPointGreenBackground
                }
                DEFAULT -> {
                    R.color.colorWhite
                }
                EMBARRASSED -> {
                    R.color.colorPointYellowBackground
                }
                CRY -> {
                    R.color.colorPointOrangeBackground
                }
                else -> {
                    R.color.colorPointRedBackground
                }
            }
    }
}