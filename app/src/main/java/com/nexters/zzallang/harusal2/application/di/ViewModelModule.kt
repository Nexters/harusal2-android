package com.nexters.zzallang.harusal2.application.di

import com.nexters.zzallang.harusal2.ui.history.HistoryViewModel
import com.nexters.zzallang.harusal2.ui.main.MainViewModel
import com.nexters.zzallang.harusal2.ui.budget.register.StartDayDefaultRegisterViewModel
import com.nexters.zzallang.harusal2.ui.budget.register.BudgetRegisterViewModel
import com.nexters.zzallang.harusal2.ui.budget.register.StartDayClickRegisterViewModel
import com.nexters.zzallang.harusal2.ui.statement.AddInputViewModel
import com.nexters.zzallang.harusal2.ui.statement.AddMemoViewModel
import com.nexters.zzallang.harusal2.ui.statement.AddStatementViewModel
import com.nexters.zzallang.harusal2.ui.budget.register.StartDayDefaultRegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HistoryViewModel(get(), get()) }
    viewModel { BudgetRegisterViewModel(get()) }
    viewModel { StartDayDefaultRegisterViewModel(get()) }
    viewModel { StartDayClickRegisterViewModel(get()) }
    viewModel { AddStatementViewModel() }
    viewModel { AddInputViewModel() }
    viewModel { AddMemoViewModel(get(), get()) }
    viewModel { MainViewModel(get(), get()) }
}
