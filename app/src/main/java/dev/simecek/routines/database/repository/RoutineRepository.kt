package dev.simecek.routines.database.repository

import androidx.lifecycle.LiveData
import dev.simecek.routines.database.dao.RoutineDao
import dev.simecek.routines.database.entity.Routine
import javax.inject.Inject

class RoutineRepository @Inject constructor(
    private val routineDao: RoutineDao
) {

    fun getAllRoutines(): LiveData<List<Routine>> {
        return routineDao.getAll()
    }

    suspend fun insert(routine: Routine) {
        routineDao.insert(routine)
    }

    suspend fun delete(routine: Routine) {
        routineDao.delete(routine)
    }

}