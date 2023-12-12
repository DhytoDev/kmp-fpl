package dev.dhyto.fpl.shared.data.sqlDelight

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import dev.dhyto.fpl.shared.FPLDatabase

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(FPLDatabase.Schema, "fpl.db")
    }
}