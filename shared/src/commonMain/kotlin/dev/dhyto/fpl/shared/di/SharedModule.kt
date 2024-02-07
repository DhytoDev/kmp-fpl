package dev.dhyto.fpl.shared.di

import dev.dhyto.fpl.shared.data.data_source.FplDataSource
import dev.dhyto.fpl.shared.data.data_source.IFplDataSource
import dev.dhyto.fpl.shared.data.remote.FPLAuthenticationApi
import dev.dhyto.fpl.shared.data.remote.FantasyPremierLeagueApi
import dev.dhyto.fpl.shared.data.repositories.FplRepository
import dev.dhyto.fpl.shared.domain.repositories.IFplRepository
import dev.dhyto.fpl.shared.domain.usecases.GetDreamTeamAndFixtures
import dev.dhyto.fpl.shared.domain.usecases.GetMyTeam
import dev.dhyto.fpl.shared.presentation.dreamTeam.DreamTeamAndFixturesViewModel
import dev.dhyto.fpl.shared.presentation.summary.ManagerInfoViewModel
import dev.dhyto.fpl.shared.presentation.team.MyTeamViewModel
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
    single { createHttpClient(get(), enableNetworkLogs = enableNetworkLogs) }

    single { FantasyPremierLeagueApi(get()) }
    single { FPLAuthenticationApi(get()) }
    single<IFplDataSource> { FplDataSource(get(), get()) }
    single<IFplRepository> { FplRepository(get()) }

    factory { GetMyTeam() }
    factory { GetDreamTeamAndFixtures(get()) }

    factory { DreamTeamAndFixturesViewModel(get()) }
    factory { ManagerInfoViewModel(get()) }
    factory { MyTeamViewModel(get()) }
}


