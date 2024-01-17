package dev.dhyto.fpl.shared.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.dhyto.fpl.shared.FPLDatabase

internal actual fun testDbDriverFactory(): SqlDriver {
    return JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).apply {
        FPLDatabase.Schema.create(this)
    }
}