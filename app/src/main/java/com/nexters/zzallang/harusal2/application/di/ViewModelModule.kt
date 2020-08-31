package com.nexters.zzallang.harusal2.application.di

import com.nexters.zzallang.harusal2.ui.MainViewModel
import com.nexters.zzallang.harusal2.ui.budget.register.BudgetRegisterViewModel
import com.nexters.zzallang.harusal2.ui.budget.register.StartDayClickRegisterViewModel
import com.nexters.zzallang.harusal2.ui.statement.edit.StatementDetailViewModel
import com.nexters.zzallang.harusal2.ui.statement.edit.StatementEditViewModel
import com.nexters.zzallang.harusal2.ui.statement.edit.StatementViewModel
import com.nexters.zzallang.harusal2.ui.statement.register.AddInputViewModel
import com.nexters.zzallang.harusal2.ui.statement.register.AddMemoViewModel
import com.nexters.zzallang.harusal2.ui.statement.register.AddStatementViewModel
import com.nexters.zzallang.harusal2.ui.budget.register.StartDayDefaultRegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { BudgetRegisterViewModel(get()) }
    viewModel { StartDayDefaultRegisterViewModel(get()) }
    viewModel { StartDayClickRegisterViewModel(get()) }
    viewModel { AddStatementViewModel() }
    viewModel { AddInputViewModel() }
    viewModel {
        AddMemoViewModel(
            get()
        )
    }
    viewModel { StatementViewModel(get()) }
    viewModel { StatementEditViewModel(get(), get()) }
    viewModel { StatementDetailViewModel(get()) }
}
