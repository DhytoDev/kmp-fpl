package dev.dhyto.fpl.shared.di

import dev.dhyto.fpl.shared.FPLDatabase
import org.koin.dsl.module

fun cacheModule() = module {
    single {
        FPLDatabase(get())
    }
}