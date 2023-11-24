package dev.dhyto.fpl.di

import dev.dhyto.fpl.presentation.DreamTeamViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { DreamTeamViewModel(get()) }
}