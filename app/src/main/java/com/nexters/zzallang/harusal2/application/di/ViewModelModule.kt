package com.nexters.zzallang.harusal2.application.di

import com.nexters.zzallang.harusal2.ui.budget.edit.BudgetEditViewModel
import com.nexters.zzallang.harusal2.ui.budget.edit.StartDayEditViewModel
import com.nexters.zzallang.harusal2.ui.history.HistoryViewModel
import com.nexters.zzallang.harusal2.ui.main.MainViewModel
import com.nexters.zzallang.harusal2.ui.budget.register.StartDayDefaultRegisterViewModel
import com.nexters.zzallang.harusal2.ui.budget.register.BudgetRegisterViewModel
import com.nexters.zzallang.harusal2.ui.budget.register.StartDayClickRegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HistoryViewModel(get(), get()) }
    viewModel { BudgetRegisterViewModel() }
    viewModel { StartDayDefaultRegisterViewModel(get()) }
    viewModel { StartDayClickRegisterViewModel(get()) }
    viewModel { MainViewModel(get(), get()) }
    viewModel { StartDayEditViewModel(get()) }
    viewModel { BudgetEditViewModel(get()) }
}
