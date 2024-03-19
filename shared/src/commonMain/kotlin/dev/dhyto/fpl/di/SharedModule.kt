package dev.dhyto.fpl.di

import dev.dhyto.fpl.data.data_source.FplDataSource
import dev.dhyto.fpl.data.data_source.IFplDataSource
import dev.dhyto.fpl.data.remote.FPLAuthenticationApi
import dev.dhyto.fpl.data.remote.FantasyPremierLeagueApi
import dev.dhyto.fpl.data.repositories.FplAuthRepository
import dev.dhyto.fpl.data.repositories.FplRepository
import dev.dhyto.fpl.domain.repositories.IFplAuthRepository
import dev.dhyto.fpl.domain.repositories.IFplRepository
import dev.dhyto.fpl.domain.usecases.GetDreamTeamAndFixtures
import dev.dhyto.fpl.domain.usecases.GetMyTeam
import dev.dhyto.fpl.presentation.dreamTeam.DreamTeamAndFixturesViewModel
import dev.dhyto.fpl.presentation.login.SignInViewModel
import dev.dhyto.fpl.presentation.summary.ManagerInfoViewModel
import dev.dhyto.fpl.presentation.team.MyTeamViewModel
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
    single { FPLAuthenticationApi() }
    single<IFplAuthRepository> { FplAuthRepository(get(), get()) }
    single<IFplDataSource> { FplDataSource(get(), get(), get()) }
    single<IFplRepository> { FplRepository(get()) }

    factory { GetMyTeam(get()) }
    factory { GetDreamTeamAndFixtures(get()) }

    factory { DreamTeamAndFixturesViewModel(get()) }
    factory { ManagerInfoViewModel(get()) }
    factory { MyTeamViewModel(get()) }
    factory { SignInViewModel(get()) }
}


