package dev.simecek.routines.database.repository

import androidx.lifecycle.LiveData
import dev.simecek.routines.database.dao.RoutineDao
import dev.simecek.routines.database.entity.Routine
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class RoutineRepository @Inject constructor(private val routineDao: RoutineDao) {

    private val sdf = SimpleDateFormat(Routine.DATE_PATTERN, Locale.getDefault())

    fun getAllRoutines(): LiveData<List<Routine>> {
        return routineDao.getAll()
    }

    suspend fun getAllRoutinesAsList(): List<Routine> {
        return routineDao.getAllAsList()
    }

    suspend fun insert(routine: Routine): Long {
        return routineDao.insert(routine)
    }

    suspend fun delete(routine: Routine) {
        routineDao.delete(routine)
    }

    suspend fun finishRoutine(routine: Routine) {
        if(routine.isFinished()) {
            routine.lastDayFinished = null
        } else {
            val today = sdf.format(Calendar.getInstance().time)
            routine.lastDayFinished = today
        }
        routineDao.update(routine)
    }

}