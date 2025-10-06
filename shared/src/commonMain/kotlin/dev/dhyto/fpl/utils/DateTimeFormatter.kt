@file:OptIn(ExperimentalTime::class)

package dev.dhyto.fpl.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import kotlin.time.Clock.System
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

fun String.convertToLocalDateTime(): LocalDateTime {
    return Instant.parse(this).toLocalDateTime(TimeZone.currentSystemDefault())
}

fun String.kickOffDayString(): String {
    val today = System.todayIn(TimeZone.currentSystemDefault())

    val kickOffDate = this.convertToLocalDateTime().date

    return when ((kickOffDate - today).days) {
        0 -> "Today"
        1 -> "Tomorrow"
        else -> "${kickOffDate.dayOfWeek}".lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
}


expect fun String.formatDate(format: String): String