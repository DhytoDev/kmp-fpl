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
import dev.dhyto.fpl.domain.usecases.GetThreeUpcomingFixtures
import dev.dhyto.fpl.domain.usecases.SaveOrGetMyTeam
import dev.dhyto.fpl.presentation.dreamTeam.DreamTeamAndFixturesViewModel
import dev.dhyto.fpl.presentation.login.SignInViewModel
import dev.dhyto.fpl.presentation.summary.ManagerInfoViewModel
import dev.dhyto.fpl.presentation.team.MyTeamViewModel
import dev.dhyto.fpl.presentation.team.PlayerSummaryViewModel
import io.ktor.client.engine.HttpClientEngine
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(enableNetworkLogs: Boolean = false, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            sharedModule(createHttpClientEngine(),  enableNetworkLogs = enableNetworkLogs),
            platformModule(),
            cacheModule(),
        )
    }

fun sharedModule(httpClientEngine: HttpClientEngine, enableNetworkLogs: Boolean) = module {
    single { createHttpClient(httpClientEngine, enableNetworkLogs = enableNetworkLogs) }

    single { FantasyPremierLeagueApi(get()) }
    single { FPLAuthenticationApi(httpClientEngine) }
    single<IFplAuthRepository> { FplAuthRepository(get(), get(), get()) }
    single<IFplDataSource> { FplDataSource(get(), get(), get()) }
    single<IFplRepository> { FplRepository(get()) }

    factory { SaveOrGetMyTeam(get()) }
    factory { GetDreamTeamAndFixtures(get()) }
    factory { GetThreeUpcomingFixtures(get()) }

    factory { DreamTeamAndFixturesViewModel(get()) }
    factory { ManagerInfoViewModel(get()) }
    factory { MyTeamViewModel(get(), get()) }
    factory { SignInViewModel(get()) }
    factory { PlayerSummaryViewModel(get()) }
}


