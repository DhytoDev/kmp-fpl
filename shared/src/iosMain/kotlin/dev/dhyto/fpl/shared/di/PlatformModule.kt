package dev.dhyto.fpl.shared.di

import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

actual fun platformModule() = module {
    single { Darwin.create() }
}