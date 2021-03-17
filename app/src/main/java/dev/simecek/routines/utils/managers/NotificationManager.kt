package dev.simecek.routines.utils.managers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.simecek.routines.R
import dev.simecek.routines.activity.MainActivity
import javax.inject.Inject

class NotificationManager @Inject constructor(@ApplicationContext var context: Context) {

    companion object {
        const val ROUTINE_REMINDER_CHANNEL_ID = "ROUTINE_CHANNEL"
        val MOTIVATION_TEXTS = arrayListOf(R.string.motivation_one, R.string.motivation_two,R.string.motivation_three)
    }

    private val openApplicationIntent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    private val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, openApplicationIntent, 0)

    private fun createRoutineNotification(title: String): Notification {
        return NotificationCompat.Builder(context, ROUTINE_REMINDER_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_logo_notification)
                .setColor(ContextCompat.getColor(context, R.color.brand))
                .setContentTitle(title)
                .setContentText(context.getString(MOTIVATION_TEXTS.random()))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()
    }

    fun showRoutineNotification(id: Int, title: String) {
        val notification = createRoutineNotification(title)
        NotificationManagerCompat.from(context).notify(id, notification)
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
            val notificationManagerManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManagerManager.createNotificationChannel(channel)
        }
    }

}