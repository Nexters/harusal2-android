package com.nexters.zzallang.harusal2.ui.setting

import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.ui.budget.edit.StartDayEditUseCase
import kotlinx.coroutines.launch

class SettingViewModel(
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