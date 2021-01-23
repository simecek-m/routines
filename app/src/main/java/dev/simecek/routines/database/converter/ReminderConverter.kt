package dev.simecek.routines.database.converter

import androidx.room.TypeConverter
import dev.simecek.routines.database.type.Reminder
import java.util.*

class ReminderConverter {

    @TypeConverter
    fun timeToInt(reminder: Reminder): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, reminder.hour)
        calendar.set(Calendar.MINUTE, reminder.minute)
        return calendar.timeInMillis
    }

    @TypeConverter
    fun longToTime(time: Long): Reminder {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        return Reminder(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))
    }

}