package dev.dhyto.fpl.shared.di

import dev.dhyto.fpl.shared.data.remote.FantasyPremierLeagueApi
import dev.dhyto.fpl.shared.data.repositories.FplRepository
import dev.dhyto.fpl.shared.domain.repositories.IFplRepository
import dev.dhyto.fpl.shared.presentation.dreamTeam.DreamTeamViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(enableNetworkLogs: Boolean = false, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            sharedModule(enableNetworkLogs = enableNetworkLogs),
            platformModule(),
            cacheModule(),
        )
    }

fun sharedModule(enableNetworkLogs: Boolean) = module {
    single { createJson() }
    single { createHttpClient(get(), get(), enableNetworkLogs = enableNetworkLogs) }

    single { FantasyPremierLeagueApi(get()) }
    single<IFplRepository> { FplRepository(get(), get()) }
    factory {
        DreamTeamViewModel(get())
    }
}


