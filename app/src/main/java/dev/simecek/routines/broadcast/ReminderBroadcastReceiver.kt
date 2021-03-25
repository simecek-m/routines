package dev.simecek.routines.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.simecek.routines.R
import dev.simecek.routines.utils.managers.NotificationManager
import dev.simecek.routines.utils.managers.ReminderManager
import javax.inject.Inject

@AndroidEntryPoint
class ReminderBroadcastReceiver: BroadcastReceiver() {

    @Inject
    @ApplicationContext
    lateinit var applicationContext: Context

    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var reminderManager: ReminderManager

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent != null) {
            val title: String = intent.getStringExtra(ReminderManager.INTENT_EXTRA_TITLE) ?: applicationContext.getString(R.string.routine)
            val id: Int = intent.getIntExtra(ReminderManager.INTENT_EXTRA_ID, 0)
            val hour: Int = intent.getIntExtra(ReminderManager.INTENT_EXTRA_HOUR, 12)
            val minute: Int = intent.getIntExtra(ReminderManager.INTENT_EXTRA_MINUTE, 0)
            notificationManager.showRoutineNotification(id, title)
            reminderManager.setReminder(id, title, hour, minute)
        }
    }

}
