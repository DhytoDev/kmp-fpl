package dev.dhyto.fpl.di

import dev.dhyto.fpl.data.local.KeyValuePersistence
import org.koin.dsl.module

actual fun platformModule() = module {
    single { KeyValuePersistence(get()) }
}