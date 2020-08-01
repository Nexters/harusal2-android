package com.nexters.zzallang.harusal2.application.di

import com.nexters.zzallang.harusal2.usecase.BudgetUseCase
import com.nexters.zzallang.harusal2.usecase.SampleUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { SampleUseCase(get()) }
    single { BudgetUseCase() }
}