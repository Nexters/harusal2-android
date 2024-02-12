package com.nexters.zzallang.harusal2.application.di

import com.nexters.zzallang.harusal2.ui.budget.edit.BudgetEditViewModel
import com.nexters.zzallang.harusal2.ui.budget.register.BudgetRegisterViewModel
import com.nexters.zzallang.harusal2.ui.budget.register.StartDayClickRegisterViewModel
import com.nexters.zzallang.harusal2.ui.budget.register.StartDayDefaultRegisterViewModel
import com.nexters.zzallang.harusal2.ui.history.HistoryViewModel
import com.nexters.zzallang.harusal2.ui.main.MainViewModel
import com.nexters.zzallang.harusal2.ui.onBoarding.OnBoardingViewModel
import com.nexters.zzallang.harusal2.ui.setting.AlarmSettingViewModel
import com.nexters.zzallang.harusal2.ui.setting.SettingViewModel
import com.nexters.zzallang.harusal2.ui.splash.SplashViewModel
import com.nexters.zzallang.harusal2.ui.statement.edit.StatementDetailViewModel
import com.nexters.zzallang.harusal2.ui.statement.edit.StatementEditViewModel
import com.nexters.zzallang.harusal2.ui.statement.register.AddInputViewModel
import com.nexters.zzallang.harusal2.ui.statement.register.AddMemoViewModel
import com.nexters.zzallang.harusal2.ui.statement.register.AddStatementViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HistoryViewModel(get(), get()) }
    viewModel { BudgetRegisterViewModel() }
    viewModel { StartDayDefaultRegisterViewModel(get()) }
    viewModel { StartDayClickRegisterViewModel(get()) }
    viewModel { AddStatementViewModel() }
    viewModel { AddInputViewModel() }
    viewModel { AddMemoViewModel(get(), get()) }
    viewModel { StatementDetailViewModel(get()) }
    viewModel { StatementEditViewModel(get(), get()) }
    viewModel { MainViewModel(get(), get()) }
    viewModel { BudgetEditViewModel(get()) }
    viewModel { AlarmSettingViewModel() }
    viewModel { SettingViewModel(get(), get(), get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { OnBoardingViewModel() }
}
