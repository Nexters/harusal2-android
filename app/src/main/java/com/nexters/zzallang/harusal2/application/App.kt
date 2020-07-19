package com.nexters.zzallang.harusal2.application

import android.app.Application
import com.nexters.zzallang.harusal2.application.di.repositoryModule
import com.nexters.zzallang.harusal2.application.di.useCaseModule
import com.nexters.zzallang.harusal2.application.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    companion object {
        lateinit var app: App
    }

    override fun onCreate() {
        super.onCreate()
        app = this

        startKoin {
            androidContext(app)
            modules(listOf(
                viewModelModule,
                repositoryModule,
                useCaseModule
            ))
        }
    }
}