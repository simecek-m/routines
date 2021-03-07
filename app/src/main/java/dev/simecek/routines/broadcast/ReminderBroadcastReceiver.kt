package dev.simecek.routines.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.simecek.routines.R
import dev.simecek.routines.notification.NotificationHelper
import dev.simecek.routines.reminder.ReminderHelper
import javax.inject.Inject

@AndroidEntryPoint
class ReminderBroadcastReceiver: BroadcastReceiver() {

    @Inject
    @ApplicationContext
    lateinit var applicationContext: Context

    @Inject
    lateinit var notificationHelper: NotificationHelper

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent != null) {
            val title: String = intent.getStringExtra(ReminderHelper.EXTRA_NAME_TITLE) ?: applicationContext.getString(R.string.routine)
            val id: Int = intent.getIntExtra(ReminderHelper.EXTRA_NAME_ID, 0)
            notificationHelper.showRoutineNotification(id, title)
        }
    }
}