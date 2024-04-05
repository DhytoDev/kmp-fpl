package dev.dhyto.fpl.data.local

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class KeyValuePersistence {
    actual val settings: Settings
        get() = NSUserDefaultsSettings.Factory().create("FPL_APP")
}