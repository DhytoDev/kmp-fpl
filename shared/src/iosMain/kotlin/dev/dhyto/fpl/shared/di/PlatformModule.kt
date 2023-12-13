package dev.dhyto.fpl.shared.di

import dev.dhyto.fpl.shared.data.sqlDelight.DriverFactory
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

actual fun platformModule() = module {
    single { Darwin.create() }
    single {
        DriverFactory().createDriver()
    }
}