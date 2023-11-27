package dev.dhyto.fpl.shared

import androidx.compose.ui.window.ComposeUIViewController
import dev.dhyto.fpl.shared.presentation.App

fun MainViewController() = ComposeUIViewController {
    App()
}