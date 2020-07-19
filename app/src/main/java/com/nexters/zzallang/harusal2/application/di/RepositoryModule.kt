package com.nexters.zzallang.harusal2.application.di

import com.nexters.zzallang.harusal2.data.repository.SampleRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { SampleRepository() }
}