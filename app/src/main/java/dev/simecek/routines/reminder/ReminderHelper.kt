package dev.simecek.routines.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.simecek.routines.broadcast.ReminderBroadcastReceiver
import java.time.ZonedDateTime
import javax.inject.Inject

class ReminderHelper @Inject constructor(@ApplicationContext var context: Context) {

    companion object {
        const val INTERVAL_DAILY: Long = 1000 * 60 * 60 * 24
        const val EXTRA_NAME_TITLE: String = "title"
        const val EXTRA_NAME_ID: String = "notificationId"
    }

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val intent = Intent(context, ReminderBroadcastReceiver::class.java)

    fun setDailyReminder(id: Int, title: String, hour: Int, minute: Int) {

        intent.putExtra(EXTRA_NAME_TITLE, title)
        intent.putExtra(EXTRA_NAME_ID, id)

        val todayReminder:ZonedDateTime = ZonedDateTime.now()
            .withHour(hour)
            .withMinute(minute)
            .withSecond(0)
            .withNano(0)

        val reminderTimeInMillis = todayReminder.toInstant().toEpochMilli()

        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                reminderTimeInMillis,
                INTERVAL_DAILY,
                getPendingIntent(id)
        )
    }

    fun removeDailyReminder(id: Int) {
        alarmManager.cancel(getPendingIntent(id))
    }

    private fun getPendingIntent(id: Int): PendingIntent {
        return PendingIntent.getBroadcast(context, id, intent, 0)
    }

}