package dev.simecek.routines.utils.managers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.simecek.routines.broadcast.ReminderBroadcastReceiver
import java.time.ZonedDateTime
import javax.inject.Inject

class ReminderManager @Inject constructor(@ApplicationContext var context: Context) {

    companion object {
        const val INTENT_EXTRA_TITLE: String = "title"
        const val INTENT_EXTRA_ID: String = "id"
        const val INTENT_EXTRA_HOUR: String = "hour"
        const val INTENT_EXTRA_MINUTE: String = "minute"
    }

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val intent = Intent(context, ReminderBroadcastReceiver::class.java)

    fun setReminder(id: Int, title: String, hour: Int, minute: Int) {

        intent.putExtra(INTENT_EXTRA_TITLE, title)
        intent.putExtra(INTENT_EXTRA_ID, id)
        intent.putExtra(INTENT_EXTRA_HOUR, hour)
        intent.putExtra(INTENT_EXTRA_MINUTE, minute)

        val now = ZonedDateTime.now()

        var reminder = now
            .withHour(hour)
            .withMinute(minute)
            .withSecond(0)
            .withNano(0)

        if(reminder.isBefore(now)) {
            reminder = reminder.plusDays(1)
        }

        val reminderTimeInMillis = reminder.toInstant().toEpochMilli()

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    reminderTimeInMillis,
                    getPendingIntent(id))
        } else {
            alarmManager.setExact(
                   AlarmManager.RTC_WAKEUP,
                    reminderTimeInMillis,
                    getPendingIntent(id))
        }
    }

    fun removeReminder(id: Int) {
        alarmManager.cancel(getPendingIntent(id))
    }

    private fun getPendingIntent(id: Int): PendingIntent {
        return PendingIntent.getBroadcast(context, id, intent, 0)
    }

}