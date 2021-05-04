package dev.simecek.routines.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import dev.simecek.routines.database.repository.RoutineRepository
import dev.simecek.routines.utils.managers.ReminderManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class RestartBroadcastReceiver: BroadcastReceiver() {

    @Inject
    lateinit var repository: RoutineRepository

    @Inject
    lateinit var reminderManager: ReminderManager

    override fun onReceive(context: Context?, intent: Intent?) {
        Timber.i("Device was restarted or application reinstalled.")
        if(intent?.action == Intent.ACTION_BOOT_COMPLETED || intent?.action == Intent.ACTION_MY_PACKAGE_REPLACED) {
            GlobalScope.launch {
                val routines = repository.getAllRoutinesAsList()
                routines.forEach {
                    reminderManager.setReminder(it.id.toInt(), it.title, it.reminder.hour, it.reminder.minute)
                }
            }
        }
    }

}