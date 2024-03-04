package dev.dhyto.fpl

import android.app.Application
import dev.dhyto.fpl.di.appModule
import dev.dhyto.fpl.di.initKoin
import moe.tlaster.precompose.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent

class FplApplication : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()

        initKoin(enableNetworkLogs = BuildConfig.DEBUG) {
            androidLogger()
            androidContext(applicationContext)
            modules(appModule)
        }
    }
}