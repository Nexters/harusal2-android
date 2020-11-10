package com.nexters.zzallang.harusal2.application.di

import com.nexters.zzallang.harusal2.usecase.BudgetUseCase
import com.nexters.zzallang.harusal2.usecase.StartDayEditUseCase
import com.nexters.zzallang.harusal2.usecase.StatementUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { StatementUseCase(get()) }
    single { BudgetUseCase(get()) }
    single {
        StartDayEditUseCase(
            get(),
            get()
        )
    }
}