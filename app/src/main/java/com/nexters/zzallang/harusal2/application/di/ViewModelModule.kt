package com.nexters.zzallang.harusal2.application.di

import com.nexters.zzallang.harusal2.ui.MainViewModel
import com.nexters.zzallang.harusal2.ui.budget.edit.BudgetChangeViewModel
import com.nexters.zzallang.harusal2.ui.budget.edit.StartDayChangeViewModel
import com.nexters.zzallang.harusal2.ui.budget.register.BudgetRegisterViewModel
import com.nexters.zzallang.harusal2.ui.budget.register.StartDayClickRegisterViewModel
import com.nexters.zzallang.harusal2.ui.budget.register.StartDayDefaultRegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { BudgetRegisterViewModel(get()) }
    viewModel { StartDayDefaultRegisterViewModel(get()) }
    viewModel { StartDayClickRegisterViewModel(get()) }
    viewModel { BudgetChangeViewModel(get()) }
    viewModel { StartDayChangeViewModel(get()) }
}
