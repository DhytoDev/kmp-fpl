package dev.dhyto.fpl.di

import org.koin.core.context.startKoin

fun initKoin() = startKoin {
    modules(
        sharedModule(createHttpClientEngine(), enableNetworkLogs = true),
        platformModule(),
        cacheModule(),
    )
}