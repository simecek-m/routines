package dev.simecek.routines.database.converter

import androidx.room.TypeConverter
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class ReminderConverter {

    companion object {
        private const val TIME_FORMATTER_PATTERN = "HH:mm"
        val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(TIME_FORMATTER_PATTERN)
    }

    @TypeConverter
    fun reminderToString(localTime: LocalTime): String {
        return timeFormatter.format(localTime)
    }

    @TypeConverter
    fun formattedTimeToReminder(formattedTime: String): LocalTime {
        return LocalTime.parse(formattedTime)
    }

}