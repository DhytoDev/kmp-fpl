package dev.dhyto.fpl.data.local

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class KeyValuePersistence(private val context: Context) {
    actual val settings: Settings
        get() = SharedPreferencesSettings.Factory(context).create()
}