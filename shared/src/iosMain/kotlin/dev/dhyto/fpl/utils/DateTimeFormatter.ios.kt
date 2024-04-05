package dev.dhyto.fpl.utils

import kotlinx.datetime.toNSDateComponents
import platform.Foundation.NSDateFormatter

actual fun String.formatDate(format: String): String {
    val localDateTime = this.convertToLocalDateTime()

    val dateFormatter = NSDateFormatter()
    dateFormatter.setDateFormat(format)

    return localDateTime.toNSDateComponents().date?.let { dateFormatter.stringFromDate(it) }
        ?: throw IllegalStateException("Could not convert kotlin date to NSDate $this")
}