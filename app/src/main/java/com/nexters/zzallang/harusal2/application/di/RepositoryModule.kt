package com.nexters.zzallang.harusal2.application.di

import com.nexters.zzallang.harusal2.data.repository.BudgetRepository
import com.nexters.zzallang.harusal2.data.repository.StatementRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { StatementRepository() }
    single { BudgetRepository() }
}