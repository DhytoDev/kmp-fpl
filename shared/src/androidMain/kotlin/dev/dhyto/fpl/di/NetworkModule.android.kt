package dev.dhyto.fpl.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android

actual fun createHttpClientEngine(): HttpClientEngine = Android.create()