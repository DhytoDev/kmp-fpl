package dev.dhyto.fpl.shared.data.sqlDelight

import app.cash.sqldelight.db.SqlDriver

expect class DriverFactory {
    fun createDriver(): SqlDriver
}