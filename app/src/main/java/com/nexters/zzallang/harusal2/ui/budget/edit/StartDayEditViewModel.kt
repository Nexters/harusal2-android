package com.nexters.zzallang.harusal2.ui.budget.edit

import com.nexters.zzallang.harusal2.base.BaseViewModel
import kotlinx.coroutines.launch

class StartDayEditViewModel(
    private val startDayEditUseCase: StartDayEditUseCase
) : BaseViewModel() {
    fun save() {
        launch {
            startDayEditUseCase.saveBudgetDate()
        }
    }

    fun initContent(): String {
        return startDayEditUseCase.initContent()
    }
}