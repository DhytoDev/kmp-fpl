package dev.dhyto.fpl.di

import dev.dhyto.fpl.data.local.KeyValuePersistence
import io.ktor.client.engine.android.Android
import org.koin.dsl.module

actual fun platformModule() = module {
    single { Android.create() }
    single { KeyValuePersistence(get()) }
}