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

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent != null) {
            val title: String = intent.getStringExtra(ReminderManager.EXTRA_NAME_TITLE) ?: applicationContext.getString(R.string.routine)
            val id: Int = intent.getIntExtra(ReminderManager.EXTRA_NAME_ID, 0)
            notificationManager.showRoutineNotification(id, title)
        }
    }

}
