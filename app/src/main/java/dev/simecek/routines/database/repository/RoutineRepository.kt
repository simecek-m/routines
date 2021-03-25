package dev.simecek.routines.database.repository

import androidx.lifecycle.LiveData
import dev.simecek.routines.database.dao.RoutineDao
import dev.simecek.routines.database.entity.Routine
import dev.simecek.routines.utils.managers.ReminderManager
import java.time.LocalDate
import javax.inject.Inject

class RoutineRepository @Inject constructor(private val routineDao: RoutineDao, private val reminderManager: ReminderManager) {


    fun getAllRoutines(): LiveData<List<Routine>> {
        return routineDao.getAll()
    }

    suspend fun getAllRoutinesAsList(): List<Routine> {
        return routineDao.getAllAsList()
    }

    suspend fun insert(routine: Routine): Long {
        val id = routineDao.insert(routine)
        reminderManager.setDailyReminder(id.toInt(), routine.title, routine.reminder.hour, routine.reminder.minute)
        return id
    }

    suspend fun delete(routine: Routine) {
        routineDao.delete(routine)
    }

    suspend fun get(id: Long) {
        routineDao.getRoutine(id)
    }

    suspend fun switchFinishState(id: Long) {
        val routine = routineDao.getRoutine(id)
        if(routine.isFinished()) {
            routine.lastFinished = null
        } else {
            routine.lastFinished = LocalDate.now()
        }
        routineDao.update(routine)
    }

    suspend fun getAllUnfinishedRoutinesAsList(): List<Routine> {
        return routineDao.getAllUnfinishedRoutinesAsList()
    }

}