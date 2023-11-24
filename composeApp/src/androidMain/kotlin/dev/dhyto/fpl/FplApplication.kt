package dev.dhyto.fpl

import android.app.Application
import dev.dhyto.fpl.shared.di.initKoin
import dev.dhyto.fpl.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent

class FplApplication : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger()
            androidContext(this@FplApplication)
            modules(appModule)
        }
    }
}