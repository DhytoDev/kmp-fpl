package dev.dhyto.fpl.utils

import dev.dhyto.fpl.utils.convertToLocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter


actual fun String.formatDate(format: String): String {
    val localDateTime = this.convertToLocalDateTime().toJavaLocalDateTime()

    return DateTimeFormatter.ofPattern(format).format(localDateTime)
}