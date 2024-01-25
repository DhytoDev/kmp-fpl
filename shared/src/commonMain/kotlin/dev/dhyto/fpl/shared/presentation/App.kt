package dev.dhyto.fpl.shared.presentation

import androidx.compose.runtime.Composable
import dev.dhyto.fpl.shared.core.theme.AppTheme
import dev.dhyto.fpl.shared.presentation.root.RootScreen
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.rememberNavigator
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