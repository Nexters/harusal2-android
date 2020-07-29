package com.nexters.zzallang.harusal2.application

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.soloader.SoLoader
import com.nexters.zzallang.harusal2.BuildConfig
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

        SoLoader.init(this, false)

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            AndroidFlipperClient.getInstance(this).apply {
                addPlugin(InspectorFlipperPlugin(this@App, DescriptorMapping.withDefaults()))
                addPlugin(DatabasesFlipperPlugin(this@App))
            }.start()
        }

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