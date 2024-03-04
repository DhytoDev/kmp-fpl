package dev.dhyto.fpl.di

import dev.dhyto.fpl.data.local.DriverFactory
import dev.dhyto.fpl.data.local.KeyValuePersistence
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

actual fun platformModule() = module {
    single { Darwin.create() }
    single {
        DriverFactory().createDriver()
    }
    single { KeyValuePersistence() }
}