package dev.dhyto.fpl.presentation

import androidx.compose.runtime.Composable
import dev.dhyto.fpl.core.theme.AppTheme
import dev.dhyto.fpl.presentation.root.RootScreen
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.rememberNavigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.KoinContext

@Composable
fun App() {
    KoinContext {
        PreComposeApp {
            val navigator = rememberNavigator()
            AppTheme {
                RootScreen(navigator)
            }
        }
    }
}