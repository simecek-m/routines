package dev.simecek.routines.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.simecek.routines.R
import dev.simecek.routines.activity.MainActivity
import javax.inject.Inject

class NotificationHelper @Inject constructor(@ApplicationContext var context: Context) {

    companion object {
        const val ROUTINE_REMINDER_CHANNEL_ID = "ROUTINE_CHANNEL"
        val MOTIVATION_TEXTS = arrayListOf(R.string.motivation_one, R.string.motivation_two,R.string.motivation_three)
    }

    private val openApplicationIntent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    private val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, openApplicationIntent, 0)

    fun createRoutineReminder(title: String): Notification {
        return NotificationCompat.Builder(context, ROUTINE_REMINDER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_logo)
            .setContentTitle(title)
            .setContentText(context.getString(MOTIVATION_TEXTS.random()))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
    }

    fun createNotificationChannels() {
        createNotificationReminderChannel()
    }

    private fun createNotificationReminderChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.notification_channel_reminders_name)
            val descriptionText = context.getString(R.string.notification_channel_reminders_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(ROUTINE_REMINDER_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}