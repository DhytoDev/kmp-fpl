package dev.dhyto.fpl.shared.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn

fun String.convertToLocalDateTime(): LocalDateTime {
    return this.toInstant().toLocalDateTime(TimeZone.currentSystemDefault())
}

fun String.kickOffDayString(): String {
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())

    val kickOffDate = this.convertToLocalDateTime().date

    return when ((kickOffDate - today).days) {
        0 -> "Today"
        1 -> "Tomorrow"
        else -> "${kickOffDate.dayOfWeek}".lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
}


expect fun String.formatDate(format: String): String