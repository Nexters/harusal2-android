package com.nexters.zzallang.harusal2.ui.setting

import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.usecase.BudgetUseCase
import com.nexters.zzallang.harusal2.usecase.StartDayEditUseCase
import com.nexters.zzallang.harusal2.usecase.StatementUseCase
import kotlinx.coroutines.launch
import java.util.*

class SettingViewModel(
    private val startDayEditUseCase: StartDayEditUseCase,
    private val budgetUseCase: BudgetUseCase,
    private val statementUseCase: StatementUseCase
) : BaseViewModel() {
    fun editBudgetDate() {
        launch {
            startDayEditUseCase.saveBudgetDate()
        }
    }

    fun deleteAllData() {
        launch {
            budgetUseCase.deleteAllBudget()
            statementUseCase.deleteAllStatement()
        }
    }
}