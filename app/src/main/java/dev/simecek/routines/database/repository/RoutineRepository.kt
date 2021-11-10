package dev.simecek.routines.database.repository

import androidx.lifecycle.LiveData
import dev.simecek.routines.database.dao.RoutineDao
import dev.simecek.routines.database.entity.Routine
import dev.simecek.routines.state.StateManager
import dev.simecek.routines.utils.managers.ReminderManager
import java.time.LocalDate
import javax.inject.Inject

class RoutineRepository @Inject constructor(
    private val routineDao: RoutineDao,
    private val reminderManager: ReminderManager,
    private val stateManager: StateManager
) {

    fun getRoutines(): LiveData<List<Routine>> {
        val userName = stateManager.getSignedInUser()
        return routineDao.getRoutines(userName)
    }

    suspend fun getRoutinesAsList(): List<Routine> {
        val userName = stateManager.getSignedInUser()
        return routineDao.getRoutinesAsList(userName)
    }

    suspend fun insert(routine: Routine): Long {
        val id = routineDao.insert(routine)
        reminderManager.setReminder(id.toInt(), routine.title, routine.reminder.hour, routine.reminder.minute)
        return id
    }

    suspend fun permanentlyDelete(routine: Routine) {
        routineDao.delete(routine)
    }

    suspend fun softDelete(routine: Routine) {
        routine.softDeleted = true
        routineDao.update(routine)
        reminderManager.removeReminder(routine.id.toInt())
    }

    suspend fun restore(routine: Routine) {
        routine.softDeleted = false
        routineDao.update(routine)
        reminderManager.setReminder(routine.id.toInt(), routine.title, routine.reminder.hour, routine.reminder.minute)
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

    suspend fun getUnfinishedRoutinesAsList(): List<Routine> {
        val userName = stateManager.getSignedInUser()
        return routineDao.getUnfinishedRoutinesAsList(userName)
    }

    fun getSoftDeletedRoutines(): LiveData<List<Routine>> {
        val userName = stateManager.getSignedInUser()
        return routineDao.getSoftDeletedRoutines(userName)
    }

}