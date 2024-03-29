package com.nexters.zzallang.harusal2.application.di

import com.nexters.zzallang.harusal2.usecase.*
import org.koin.dsl.module

val useCaseModule = module {
    single { StatementUseCase(get()) }
    single { BudgetUseCase(get()) }
    single { StartDayEditUseCase(get(), get()) }
    single { GetRemainMoneyUseCase(get(), get()) }
    single { GetSpentMoneyStatusUseCase() }
    single { GetTodayBudgetUseCase(get(), get()) }
    single { GetRemainDayUseCase(get()) }
}