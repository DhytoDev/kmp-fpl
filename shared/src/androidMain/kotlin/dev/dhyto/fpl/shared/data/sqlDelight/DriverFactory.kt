package dev.dhyto.fpl.shared.data.sqlDelight

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import dev.dhyto.fpl.shared.FPLDatabase

actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(FPLDatabase.Schema, context, "fpl.db")
    }
}