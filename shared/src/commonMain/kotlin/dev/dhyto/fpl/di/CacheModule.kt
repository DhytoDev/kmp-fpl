package dev.dhyto.fpl.di

import dev.dhyto.fpl.FPLDatabase
import org.koin.dsl.module

fun cacheModule() = module {
    single {
        FPLDatabase(get())
    }
}