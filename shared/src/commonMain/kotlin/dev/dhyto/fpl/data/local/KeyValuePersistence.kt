package dev.dhyto.fpl.data.local

import com.russhwolf.settings.Settings

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class KeyValuePersistence {
    val settings: Settings
}