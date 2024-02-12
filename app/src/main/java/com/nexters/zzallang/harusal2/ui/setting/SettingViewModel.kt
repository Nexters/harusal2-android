package com.nexters.zzallang.harusal2.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexters.zzallang.harusal2.data.entity.Budget
import com.nexters.zzallang.harusal2.usecase.BudgetUseCase
import com.nexters.zzallang.harusal2.usecase.StartDayEditUseCase
import com.nexters.zzallang.harusal2.usecase.StatementUseCase
import kotlinx.coroutines.launch

class SettingViewModel(
    private val startDayEditUseCase: StartDayEditUseCase,
    private val budgetUseCase: BudgetUseCase,
    private val statementUseCase: StatementUseCase
) : ViewModel() {
    fun editBudgetDate() {
        viewModelScope.launch {
            startDayEditUseCase.saveBudgetDate()
        }
    }

    suspend fun findBudget(): Budget {
        return budgetUseCase.findRecentBudget();
    }

    fun deleteAllData() {
        viewModelScope.launch {
            budgetUseCase.deleteAllBudget()
            statementUseCase.deleteAllStatement()
        }
    }
}
