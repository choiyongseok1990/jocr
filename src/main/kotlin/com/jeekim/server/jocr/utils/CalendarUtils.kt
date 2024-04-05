package com.jeekim.server.jocr.utils

import com.jeekim.server.jocr.dto.ocr.GeneratedDate
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import java.util.regex.Pattern

object CalendarUtils {
    private val DATE_FORMAT_REGEX = "[0-9]{8}"

    fun checkDateFormatAndGet(value: String?): GeneratedDate {
        val today = LocalDate.now()

        if (value != null && Pattern.matches(DATE_FORMAT_REGEX, value)) {
            try {
                val valueDate = parseAndGetDate(value)

                if (today.isEqual(valueDate) || today.isAfter(valueDate)) {
                    return GeneratedDate(toDateFormat(valueDate), false)
                }
            } catch (e: Exception) {
                return GeneratedDate(toDateFormat(today), true)
            }
        }

        return GeneratedDate(toDateFormat(today), true)
    }

    private fun parseAndGetDate(value: String): LocalDate {
        val year = value.substring(0, 4).toInt()
        val month = value.substring(4, 6).toInt()
        val day = value.substring(6, 8).toInt()

        return LocalDate.of(year, month, day)
    }

    private fun toDateFormat(date: LocalDate): String {
        val year = String.format("%04d", date.year)
        val month = String.format("%02d", date.monthValue)
        val day = String.format("%02d", date.dayOfMonth)

        return "$year-$month-$day"
    }
}