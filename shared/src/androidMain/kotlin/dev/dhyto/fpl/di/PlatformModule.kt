package dev.dhyto.fpl.shared.di

import dev.dhyto.fpl.shared.data.local.KeyValuePersistence
import io.ktor.client.engine.android.Android
import org.koin.dsl.module

actual fun platformModule() = module {
    single { Android.create() }
    single { KeyValuePersistence(get()) }
}