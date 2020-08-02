package com.nexters.zzallang.harusal2.application.di

import com.nexters.zzallang.harusal2.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
}