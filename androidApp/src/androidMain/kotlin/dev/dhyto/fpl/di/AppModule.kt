package dev.dhyto.fpl.di

import dev.dhyto.fpl.data.local.DriverFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single { DriverFactory(androidContext()).createDriver() }
}