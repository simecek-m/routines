package dev.simecek.routines.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import dev.simecek.routines.database.repository.RoutineRepository
import dev.simecek.routines.utils.managers.ReminderManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RebootBroadcastReceiver: BroadcastReceiver() {

    @Inject
    lateinit var repository: RoutineRepository

    @Inject
    lateinit var reminderManager: ReminderManager

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            GlobalScope.launch {
                val routines = repository.getAllRoutinesAsList()
                routines.forEach {
                    reminderManager.setDailyReminder(it.id.toInt(), it.title, it.reminder.hour, it.reminder.minute)
                }
            }
        }
    }

}