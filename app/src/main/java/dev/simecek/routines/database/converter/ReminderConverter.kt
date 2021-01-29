package dev.simecek.routines.database.converter

import androidx.room.TypeConverter
import dev.simecek.routines.database.type.Reminder
import java.util.*

// Converter for Reminder database field
// calculate minutes from Reminder time to save it into DB and vice versa
class ReminderConverter {

    companion object {
        const val MINUTES = 60
    }

    @TypeConverter
    fun reminderToMinutes(reminder: Reminder): Int {
        return reminder.minute + reminder.hour * MINUTES
    }

    @TypeConverter
    fun minutesToReminder(timeInMinutes: Int): Reminder {
        val hours = timeInMinutes / MINUTES
        val minutes = timeInMinutes - hours * MINUTES
        return Reminder(hours, minutes)
    }

}